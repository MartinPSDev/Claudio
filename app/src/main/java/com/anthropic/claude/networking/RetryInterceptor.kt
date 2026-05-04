package com.anthropic.claude.networking

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * OkHttp interceptor that implements exponential backoff retry for
 * transient failures and server errors.
 *
 * Retry conditions:
 *   - [IOException] (network failures)
 *   - HTTP 429 (Too Many Requests) — respects Retry-After header
 *   - HTTP 500+ (Server errors)
 *
 * Does NOT retry:
 *   - Client errors (4xx except 429)
 *   - Successful responses (2xx, 3xx)
 */
class RetryInterceptor(
    private val maxRetries: Int = 3,
    private val initialDelayMs: Long = 500L,
) : Interceptor {

    companion object {
        private const val TAG = "RetryInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var lastException: IOException? = null
        var lastResponse: Response? = null

        for (attempt in 0..maxRetries) {
            if (attempt > 0) {
                val delay = calculateDelay(attempt, lastResponse)
                Log.d(TAG, "Retry #$attempt after ${delay}ms for ${request.url}")
                Thread.sleep(delay)
            }

            try {
                lastResponse?.close()
                val response = chain.proceed(request)

                if (response.isSuccessful || !shouldRetry(response.code)) {
                    return response
                }

                lastResponse = response
            } catch (e: IOException) {
                lastException = e
                Log.w(TAG, "Request failed (attempt ${attempt + 1}/${maxRetries + 1}): ${e.message}")
            }
        }

        // Exhausted retries — return last response or throw last exception
        lastResponse?.let { return it }
        throw lastException ?: IOException("Request failed after ${maxRetries + 1} attempts")
    }

    private fun shouldRetry(statusCode: Int): Boolean {
        return statusCode == 429 || statusCode >= 500
    }

    private fun calculateDelay(attempt: Int, lastResponse: Response?): Long {
        // Respect Retry-After header if present (value in seconds)
        if (lastResponse?.code == 429) {
            val retryAfter = lastResponse.header("Retry-After")
            retryAfter?.toLongOrNull()?.let { seconds ->
                return seconds * 1000L
            }
        }

        // Exponential backoff: 500ms, 1000ms, 2000ms, ...
        return initialDelayMs * (1L shl (attempt - 1))
    }
}
