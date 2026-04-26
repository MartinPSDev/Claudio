package com.anthropic.claude.networking

import com.anthropic.claude.api.chat.messages.StreamEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.ByteString.Companion.encodeUtf8
import okio.Options
import java.io.IOException

/**
 * A robust client that parses Server-Sent Events (SSE).
 * It uses Okio `BufferedSource` and `Options` to efficiently scan for SSE prefixes.
 */
class SseClient(
    private val httpClient: OkHttpClient,
) {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private val sseOptions = Options.of(
        "\r\n".encodeUtf8(),
        "\r".encodeUtf8(),
        "\n".encodeUtf8(),
        "data: ".encodeUtf8(),
        "data:".encodeUtf8(),
        "data\r\n".encodeUtf8(),
        "data\r".encodeUtf8(),
        "data\n".encodeUtf8(),
        "id: ".encodeUtf8(),
        "id:".encodeUtf8(),
        "id\r\n".encodeUtf8(),
        "id\r".encodeUtf8(),
        "id\n".encodeUtf8(),
        "event: ".encodeUtf8(),
        "event:".encodeUtf8(),
        "event\r\n".encodeUtf8(),
        "event\r".encodeUtf8(),
        "event\n".encodeUtf8(),
        "retry: ".encodeUtf8(),
        "retry:".encodeUtf8()
    )

    fun streamEvents(request: Request): Flow<StreamEvent> = flow {
        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            val errorBody = response.body?.string()
            response.close()
            throw IOException("Unexpected SSE response code ${response.code}: $errorBody")
        }

        val source = response.body?.source() ?: throw IOException("Empty response body in SSE")

        var currentEvent = ""
        val currentData = StringBuilder()

        try {
            while (!source.exhausted()) {
                when (source.select(sseOptions)) {
                    0, 1, 2 -> {
                        // Empty line triggers event dispatch
                        val dataString = currentData.toString()
                        if (dataString.isNotEmpty() || currentEvent.isNotEmpty()) {
                            try {
                                if (dataString.isNotBlank()) {
                                    val streamEvent = json.decodeFromString<StreamEvent>(dataString)
                                    emit(streamEvent)
                                }
                            } catch (e: Exception) {
                                // Ignore unparseable events (e.g. unknown experimental events)
                            }
                            
                            currentEvent = ""
                            currentData.clear()
                        }
                    }
                    3, 4 -> {
                        // "data: " or "data:"
                        currentData.append(source.readUtf8LineStrict())
                    }
                    5, 6, 7 -> {
                        // Empty data
                    }
                    8, 9 -> {
                        // "id: " or "id:"
                        source.readUtf8LineStrict()
                    }
                    10, 11, 12 -> {
                        // Empty id
                    }
                    13, 14 -> {
                        // "event: " or "event:"
                        currentEvent = source.readUtf8LineStrict()
                    }
                    15, 16, 17 -> {
                        // Empty event
                    }
                    18, 19 -> {
                        // "retry: " or "retry:"
                        source.readUtf8LineStrict()
                    }
                    -1 -> {
                        // Fallback, unknown prefix. Read line to advance.
                        source.readUtf8LineStrict()
                    }
                }
            }
        } finally {
            response.close()
        }
    }.flowOn(Dispatchers.IO)
}
