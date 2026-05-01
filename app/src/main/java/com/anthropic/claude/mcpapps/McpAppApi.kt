package com.anthropic.claude.mcpapps

import android.util.Log
import com.anthropic.claude.streaming.SseFrame
import com.anthropic.claude.streaming.SseLineParser
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Client for communicating with MCP (Model Context Protocol) app servers
 * via the Anthropic SSE proxy.
 *
 * Endpoints:
 *   - POST /v1/toolbox/shttp/mcp/<server-path>
 *
 * Headers:
 *   - Accept: text/event-stream
 *   - X-Organization-UUID
 *   - X-Mcp-Client-Session-Id
 *   - X-MCP-Client-Name: ClaudeAndroid
 *
 * Response header:
 *   - Mcp-Session-Id (persisted for the lifecycle of the connection)
 *
 * Content types:
 *   - text/html;profile=mcp-app
 *   - text/html+mcp
 */
class McpAppApi(
    private val httpClient: OkHttpClient,
    private val baseUrl: String,
) {
    companion object {
        private const val TAG = "McpAppApi"
        private const val CLIENT_NAME = "ClaudeAndroid"
        private const val PROTOCOL_VERSION = "1.260416.20"
        private const val PROTOCOL_DATE = "2026-01-26"
        private val JSON_MEDIA = "application/json".toMediaType()
    }

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Initializes a session with the given MCP server.
     *
     * Sends the `initialize` JSON-RPC method and reads the SSE response
     * to extract the session ID.
     *
     * @param serverId UUID of the MCP server.
     * @param orgId Organization UUID.
     * @param clientSessionId Random per-session identifier.
     * @return The [McpSession] if initialization succeeds, null otherwise.
     */
    suspend fun initializeSession(
        serverId: String,
        orgId: String,
        clientSessionId: String,
    ): McpSession? {
        Log.i(TAG, "Initializing MCP session for server: $serverId")

        val initBody = McpJsonRpcRequest(
            method = "initialize",
            params = McpInitParams(
                protocolVersion = PROTOCOL_VERSION,
                clientInfo = McpClientInfo(
                    name = CLIENT_NAME,
                    version = PROTOCOL_DATE,
                ),
            ),
        )

        val request = Request.Builder()
            .url("$baseUrl/v1/toolbox/shttp/mcp/$serverId")
            .post(json.encodeToString(McpJsonRpcRequest.serializer(), initBody)
                .toRequestBody(JSON_MEDIA))
            .header("Accept", "text/event-stream")
            .header("X-Organization-UUID", orgId)
            .header("X-Mcp-Client-Session-Id", clientSessionId)
            .header("X-MCP-Client-Name", CLIENT_NAME)
            .build()

        val response = httpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            Log.e(TAG, "MCP init failed: ${response.code}")
            return null
        }

        val mcpSessionId = response.header("Mcp-Session-Id")

        // Parse SSE response for initialize result
        val body = response.body ?: return null
        val reader = BufferedReader(InputStreamReader(body.byteStream()))
        val parser = SseLineParser()
        var initResult: JsonElement? = null

        reader.useLines { lines ->
            for (line in lines) {
                val frame = parser.feedLine(line) ?: continue
                initResult = tryParseInitResult(frame)
                if (initResult != null) break
            }
        }

        if (initResult != null) {
            Log.i(TAG, "MCP session initialized for server: $serverId")
            return McpSession(
                serverId = serverId,
                sessionId = mcpSessionId,
                clientSessionId = clientSessionId,
                capabilities = initResult,
            )
        }

        return null
    }

    /**
     * Sends a JSON-RPC request to an MCP server and reads SSE data lines
     * looking for a response matching [requestId].
     *
     * @param serverId MCP server UUID.
     * @param orgId Organization UUID.
     * @param session Active MCP session.
     * @param requestBody JSON-RPC request body.
     * @param requestId The `id` field of the JSON-RPC request.
     * @return Parsed JSON response, or null on failure.
     */
    suspend fun sendRequest(
        serverId: String,
        orgId: String,
        session: McpSession,
        requestBody: String,
        requestId: String,
    ): JsonElement? {
        val request = Request.Builder()
            .url("$baseUrl/v1/toolbox/shttp/mcp/$serverId")
            .post(requestBody.toRequestBody(JSON_MEDIA))
            .header("Accept", "text/event-stream")
            .header("X-Organization-UUID", orgId)
            .header("X-Mcp-Client-Session-Id", session.clientSessionId)
            .header("X-MCP-Client-Name", CLIENT_NAME)
            .apply {
                session.sessionId?.let { header("Mcp-Session-Id", it) }
            }
            .build()

        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) return null

        val body = response.body ?: return null
        val reader = BufferedReader(InputStreamReader(body.byteStream()))
        val parser = SseLineParser()
        var linesScanned = 0

        reader.useLines { lines ->
            for (line in lines) {
                val frame = parser.feedLine(line) ?: continue
                linesScanned++
                val result = tryMatchResponse(frame, requestId)
                if (result != null) return result
            }
        }

        Log.w(TAG, "Scanned $linesScanned SSE data lines, no match for requestId=$requestId")
        return null
    }

    /**
     * Discovers the resource URI for an MCP server.
     */
    suspend fun discoverResourceUri(serverId: String, orgId: String, session: McpSession): String? {
        Log.d(TAG, "discoverResourceUri: serverId=$serverId")

        val rpcRequest = McpJsonRpcRequest(
            method = "resources/list",
            id = "discover-${System.currentTimeMillis()}",
        )

        val result = sendRequest(
            serverId = serverId,
            orgId = orgId,
            session = session,
            requestBody = json.encodeToString(McpJsonRpcRequest.serializer(), rpcRequest),
            requestId = rpcRequest.id!!,
        )

        return result?.jsonObject
            ?.get("resources")
            ?.jsonObject
            ?.get("uri")
            ?.jsonPrimitive
            ?.content
    }

    // ── Private ──────────────────────────────────────────────────────────────

    private fun tryParseInitResult(frame: SseFrame): JsonElement? {
        if (frame.data.isBlank()) return null
        return try {
            val obj = json.parseToJsonElement(frame.data).jsonObject
            obj["result"]
        } catch (e: Exception) {
            Log.w(TAG, "Failed to parse SSE data line as JSON: ${frame.data}, error: ${e.message}")
            null
        }
    }

    private fun tryMatchResponse(frame: SseFrame, requestId: String): JsonElement? {
        if (frame.data.isBlank()) return null
        return try {
            val obj = json.parseToJsonElement(frame.data).jsonObject
            val id = obj["id"]?.jsonPrimitive?.content
            if (id == requestId) obj["result"] else null
        } catch (e: Exception) {
            Log.w(TAG, "Failed to parse SSE data line as JSON: ${frame.data}, error: ${e.message}")
            null
        }
    }
}

/** Active MCP session state. */
data class McpSession(
    val serverId: String,
    val sessionId: String?,
    val clientSessionId: String,
    val capabilities: JsonElement?,
)

/** JSON-RPC request wrapper for MCP. */
@Serializable
data class McpJsonRpcRequest(
    val jsonrpc: String = "2.0",
    val method: String,
    val params: McpInitParams? = null,
    val id: String? = null,
)

@Serializable
data class McpInitParams(
    val protocolVersion: String,
    val clientInfo: McpClientInfo,
)

@Serializable
data class McpClientInfo(
    val name: String,
    val version: String,
)
