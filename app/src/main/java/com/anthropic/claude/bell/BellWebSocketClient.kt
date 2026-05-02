package com.anthropic.claude.bell

import android.util.Log

/**
 * Manages the WebSocket connection for Bell (voice) mode.
 *
 * The WebSocket URL is derived from the HTTP base URL by replacing
 * the protocol:
 *   - `https://` → `wss://`
 *   - `http://`  → `ws://`
 *
 * Messages are exchanged as JSON using the [BellApiModels] hierarchy.
 */
class BellWebSocketClient(
    private val httpClient: okhttp3.OkHttpClient,
) {
    companion object {
        private const val TAG = "BellWebSocket"
    }

    private var webSocket: okhttp3.WebSocket? = null

    /**
     * Converts an HTTP/HTTPS base URL to its WebSocket equivalent.
     *
     * @param baseUrl The base URL (e.g. `https://claude.ai`).
     * @return The WebSocket URL (e.g. `wss://claude.ai`).
     * @throws IllegalArgumentException if the protocol is unknown.
     */
    fun toWebSocketUrl(baseUrl: String): String {
        return when {
            baseUrl.startsWith("https://") -> baseUrl.replaceFirst("https://", "wss://")
            baseUrl.startsWith("http://") -> baseUrl.replaceFirst("http://", "ws://")
            else -> throw IllegalArgumentException("Unknown protocol in URL: $baseUrl")
        }
    }

    /**
     * Opens a WebSocket connection to the Bell voice endpoint.
     *
     * @param baseUrl The HTTP base URL (will be converted to ws/wss).
     * @param path The WebSocket path (e.g. `/bell/ws`).
     * @param listener Callback for WebSocket events.
     */
    fun connect(
        baseUrl: String,
        path: String,
        listener: BellWebSocketListener,
    ) {
        val wsUrl = toWebSocketUrl(baseUrl) + path

        val request = okhttp3.Request.Builder()
            .url(wsUrl)
            .build()

        webSocket = httpClient.newWebSocket(request, object : okhttp3.WebSocketListener() {
            override fun onOpen(webSocket: okhttp3.WebSocket, response: okhttp3.Response) {
                Log.i(TAG, "WebSocket connected")
                listener.onConnected()
            }

            override fun onMessage(webSocket: okhttp3.WebSocket, text: String) {
                listener.onMessage(text)
            }

            override fun onClosing(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
                Log.i(TAG, "WebSocket closing: $code $reason")
                webSocket.close(code, reason)
                listener.onDisconnected(code, reason)
            }

            override fun onClosed(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
                Log.i(TAG, "WebSocket closed: $code $reason")
                listener.onDisconnected(code, reason)
            }

            override fun onFailure(
                webSocket: okhttp3.WebSocket,
                t: Throwable,
                response: okhttp3.Response?,
            ) {
                Log.e(TAG, "WebSocket failure", t)
                listener.onError(t)
            }
        })
    }

    /**
     * Sends a JSON message through the WebSocket.
     */
    fun send(json: String): Boolean {
        return webSocket?.send(json) ?: false
    }

    /**
     * Closes the WebSocket connection.
     */
    fun disconnect() {
        webSocket?.close(1000, "Client disconnect")
        webSocket = null
    }
}

/**
 * Callback interface for Bell WebSocket events.
 */
interface BellWebSocketListener {
    fun onConnected()
    fun onMessage(text: String)
    fun onDisconnected(code: Int, reason: String)
    fun onError(error: Throwable)
}
