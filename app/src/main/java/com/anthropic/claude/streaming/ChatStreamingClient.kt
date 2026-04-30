package com.anthropic.claude.streaming

import android.util.Log
import com.anthropic.claude.networking.AnthropicApiClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Manages the SSE connection for chat message streaming.
 *
 * Opens a POST request to the chat completion endpoint with
 * `Accept: text/event-stream` and emits [StreamEvent]s as they arrive.
 *
 * The flow automatically completes on `message_stop` or connection close.
 * Cancelling the collecting coroutine closes the HTTP connection.
 */
class ChatStreamingClient(
    private val apiClient: AnthropicApiClient,
) {
    companion object {
        private const val TAG = "ChatStreamingClient"
        private val JSON_MEDIA = "application/json".toMediaType()
    }

    /**
     * Opens an SSE stream for the given chat completion request.
     *
     * @param orgId Organization UUID.
     * @param conversationId Conversation UUID.
     * @param requestBody JSON-serialized completion request body.
     * @return A [Flow] of [StreamEvent]s.
     */
    fun stream(
        orgId: String,
        conversationId: String,
        requestBody: String,
    ): Flow<StreamEvent> = callbackFlow {
        val url = apiClient.buildUrl(
            "/api/organizations/$orgId/chat_conversations/$conversationId/completion"
        )

        val request = Request.Builder()
            .url(url)
            .post(requestBody.toRequestBody(JSON_MEDIA))
            .header("Accept", "text/event-stream")
            .header("Cache-Control", "no-cache")
            .build()

        val response = apiClient.httpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            Log.e(TAG, "Stream failed: ${response.code}")
            close(StreamingException("HTTP ${response.code}: ${response.message}"))
            return@callbackFlow
        }

        val body = response.body
        if (body == null) {
            close(StreamingException("Empty response body"))
            return@callbackFlow
        }

        val reader = BufferedReader(InputStreamReader(body.byteStream(), Charsets.UTF_8))
        val parser = SseLineParser()

        try {
            var line: String?
            while (isActive) {
                line = reader.readLine() ?: break
                val frame = parser.feedLine(line) ?: continue
                val event = StreamEventDeserializer.deserialize(frame)
                send(event)

                if (event is StreamEvent.MessageStop) {
                    break
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Stream error", e)
            close(e)
        } finally {
            reader.close()
            body.close()
            response.close()
        }

        close()
    }.flowOn(Dispatchers.IO)

    /**
     * Opens an SSE stream for a retry/regeneration of the last message.
     */
    fun retryStream(
        orgId: String,
        conversationId: String,
        requestBody: String,
    ): Flow<StreamEvent> = stream(orgId, conversationId, requestBody)
}

class StreamingException(message: String) : Exception(message)
