package com.anthropic.claude.repository

import com.anthropic.claude.api.mcp.CreateMcpRemoteServerRequest
import com.anthropic.claude.api.mcp.McpServer
import com.anthropic.claude.api.mcp.McpTool
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.serialization.json.Json

/**
 * Repository for MCP server management.
 */
class McpRepository(
    private val apiClient: AnthropicApiClient,
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun createRemoteServer(request: CreateMcpRemoteServerRequest): ApiResult<McpServer> =
        execute { apiClient.createMcpRemoteServer(request) }

    suspend fun deleteServer(serverId: String): ApiResult<Unit> =
        try {
            val response = apiClient.deleteMcpServer(serverId)
            if (response.isSuccessful) ApiResult.Success(Unit)
            else ApiResult.Error(response.code, null)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }

    suspend fun getTools(serverId: String): ApiResult<List<McpTool>> =
        execute { apiClient.getMcpServerTools(serverId) }

    private suspend inline fun <reified T> execute(
        crossinline call: suspend () -> okhttp3.Response,
    ): ApiResult<T> = try {
        val response = call()
        val body = response.body?.string() ?: ""
        if (response.isSuccessful) ApiResult.Success(json.decodeFromString(body))
        else ApiResult.Error(response.code, body.takeIf { it.isNotBlank() })
    } catch (e: Exception) {
        ApiResult.NetworkError
    }
}
