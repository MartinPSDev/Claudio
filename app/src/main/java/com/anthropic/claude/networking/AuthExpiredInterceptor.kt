package com.anthropic.claude.networking

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * OkHttp interceptor that detects 401 Unauthorized responses
 * and invokes the logout callback.
 *
 * Placed last in the interceptor chain so it processes the final response.
 * Excludes login/auth endpoints from triggering logout to avoid
 * infinite loops during authentication flows.
 */
class AuthExpiredInterceptor(
    private val onAuthExpired: () -> Unit,
) : Interceptor {

    companion object {
        private const val TAG = "AuthExpiredInterceptor"

        /** Paths that should NOT trigger the auth-expired callback. */
        private val EXCLUDED_PATHS = listOf(
            "/api/auth/login",
            "/api/auth/send_magic_link",
            "/api/auth/verify_magic_link",
            "/api/auth/google",
            "/api/auth/apple",
            "/api/bootstrap",
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            val path = request.url.encodedPath
            val isAuthEndpoint = EXCLUDED_PATHS.any { path.startsWith(it) }

            if (!isAuthEndpoint) {
                Log.w(TAG, "401 on ${request.method} $path — triggering auth expired")
                onAuthExpired()
            }
        }

        return response
    }
}
