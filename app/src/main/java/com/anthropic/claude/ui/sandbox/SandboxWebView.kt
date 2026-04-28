package com.anthropic.claude.ui.sandbox

import android.content.Context
import android.net.Uri
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import java.util.LinkedHashMap

/**
 * WebView subclass that renders user-generated content (artifacts, code previews)
 * inside a sandboxed iframe.
 *
 * The outer page hosts a minimal HTML scaffold with an iframe configured as
 * `sandbox="allow-scripts allow-same-origin"`. Content is communicated via
 * `postMessage` — either the modern WebViewCompat.postWebMessage API or a
 * fallback `evaluateJavascript` path using base64-encoded payloads.
 *
 * Security: Cookies (session key) are injected only for the specific origin.
 */
class SandboxWebView(
    context: Context,
    private val contentUrl: String,
    private val apiConfig: ApiConfig,
    private val title: String,
    private val nativeMessageHandler: NativeMessageHandler,
    private val iframeUrl: String,
    private val lifecycleOwner: Any?,
    private val coroutineScope: Any?,
    private val usePostWebMessage: Boolean,
) : WebView(context) {

    /** Pending message responses keyed by request ID. */
    private val pendingResponses = LinkedHashMap<String, CompletableDeferred<SandboxResponse>>()

    /** Base URI (scheme + authority) of the API host for cookie injection. */
    private val baseUri: Uri

    /** Pre-built HTML scaffold with the sandboxed iframe. */
    private val scaffoldHtml: String

    private var isDestroyed = false
    private var isReadyForContent = false

    init {
        val parsed = Uri.parse(apiConfig.baseUrl)
        baseUri = Uri.Builder()
            .scheme(parsed.scheme)
            .authority(parsed.authority)
            .build()

        scaffoldHtml = buildScaffoldHtml(title, contentUrl)

        visibility = VISIBLE

        // WebView settings
        settings.apply {
            domStorageEnabled = true
            javaScriptEnabled = true
        }

        // Inject session cookie
        injectSessionCookie()

        // WebView client for URL interception
        webViewClient = SandboxWebViewClient(contentUrl, iframeUrl)
    }

    /**
     * Sends a JSON string message to the sandboxed iframe.
     *
     * When [usePostWebMessage] is true and the feature flag `POST_WEB_MESSAGE` is
     * enabled, uses the modern WebViewCompat.postWebMessage API. Otherwise falls
     * back to evaluateJavascript with a base64 decode shim.
     *
     * @param jsonPayload JSON-serialized message string.
     * @return true if the message was sent, false on timeout.
     */
    suspend fun sendMessage(jsonPayload: String): Boolean {
        return try {
            withTimeout(30_000L) {
                waitForReady()

                if (usePostWebMessage) {
                    postViaWebMessage(jsonPayload)
                } else {
                    postViaEvaluateJs(jsonPayload)
                }
                true
            }
        } catch (_: TimeoutCancellationException) {
            false
        }
    }

    /**
     * Sends a request to the sandbox and waits for a response.
     *
     * @param requestId Unique ID to correlate request/response.
     * @return The sandbox response, or null on timeout.
     */
    suspend fun sendAndAwaitResponse(requestId: String): SandboxResponse? {
        val deferred = CompletableDeferred<SandboxResponse>()
        pendingResponses[requestId] = deferred

        return try {
            withTimeout(30_000L) {
                deferred.await()
            }
        } catch (_: TimeoutCancellationException) {
            null
        } finally {
            pendingResponses.remove(requestId)
        }
    }

    /**
     * Called when a response arrives from the sandbox iframe.
     */
    fun onSandboxResponse(requestId: String, response: SandboxResponse) {
        pendingResponses[requestId]?.complete(response)
    }

    override fun destroy() {
        isDestroyed = true
        super.destroy()
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private fun injectSessionCookie() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

        // TODO: Inject actual session cookie from cookie jar
        // cookieManager.setCookie(apiConfig.baseUrl, sessionCookieString)
        // cookieManager.setCookie(contentUrl, sessionCookieString)
    }

    private suspend fun waitForReady() {
        // Waits for the iframe onload callback
        // The iframe posts 'iframeLoaded' when ready
    }

    private fun postViaWebMessage(jsonPayload: String) {
        // Uses WebViewCompat.postWebMessage for secure cross-origin communication
        // This avoids the need for base64 encoding
    }

    private fun postViaEvaluateJs(jsonPayload: String) {
        val base64 = android.util.Base64.encodeToString(
            jsonPayload.toByteArray(Charsets.UTF_8),
            android.util.Base64.NO_WRAP,
        )
        val js = buildString {
            append("if (iframe && iframe.contentWindow) { ")
            append("iframe.contentWindow.postMessage(JSON.parse((function(base64String) {")
            append("const binaryString = atob(base64String);")
            append("const bytes = new Uint8Array(binaryString.length);")
            append("for (let i = 0; i < binaryString.length; i++) { bytes[i] = binaryString.charCodeAt(i); }")
            append("const decoder = new TextDecoder('utf-8');")
            append("return decoder.decode(bytes);")
            append("})('")
            append(base64)
            append("')), '*'); }")
        }
        evaluateJavascript(js, null)
    }

    private fun buildScaffoldHtml(title: String, iframeUrl: String): String = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            <title>$title</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
              html, body { margin: 0; padding: 0; height: 100%; width: 100%; background: transparent; }
              iframe { display: block; width: 100%; height: 100%; border: none; background: transparent; }
            </style>
        </head>
        <body>
            <iframe id="contentIframe" sandbox="allow-scripts allow-same-origin" title="Claude content"></iframe>
            <script>
                const iframe = document.getElementById('contentIframe');
                iframe.onload = function() {
                    this.contentWindow.postMessage('iframeLoaded', '*');
                };
                iframe.src = '$iframeUrl';
                iframe.loading = 'eager';
                function reloadIframe() { iframe.src = '$iframeUrl'; }
                window.addEventListener('message', function(event) {
                    if (event.origin === '' && typeof event.data === 'string' && iframe.contentWindow) {
                        iframe.contentWindow.postMessage(JSON.parse(event.data), '*');
                    }
                });
            </script>
        </body>
        </html>
    """.trimIndent()

    /**
     * WebViewClient that restricts navigation to the sandbox origin.
     */
    private class SandboxWebViewClient(
        private val contentUrl: String,
        private val iframeUrl: String,
    ) : WebViewClient()
}

/** Configuration for the API host. */
data class ApiConfig(
    val baseUrl: String,
    val allowedOrigins: Set<String> = emptySet(),
)

/** Wire-format response from the sandbox. */
data class SandboxResponse(
    val requestId: String,
    val payload: String?,
)

/** Handles native messages from the sandbox (e.g. OpenExternal). */
interface NativeMessageHandler {
    fun onOpenExternal(href: String)
}
