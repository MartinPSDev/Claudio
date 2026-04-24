package com.anthropic.claude.networking

import android.content.Context

/**
 * Factory that wires together the OkHttpClient, interceptors, and AnthropicApiClient.
 */
object NetworkingModule {

    /**
     * Build and return a fully-configured [AnthropicApiClient].
     *
     * @param context      Application context (used for cookie persistence).
     * @param onAuthExpired Callback invoked by [AuthExpiredInterceptor] on 401 responses.
     */
    fun provideApiClient(
        context: Context,
        onAuthExpired: () -> Unit,
    ): AnthropicApiClient {
        val cookieJar = provideCookieJar(context)
        val sessionInterceptor = SessionInterceptor(cookieJar)
        val authExpiredInterceptor = AuthExpiredInterceptor(onAuthExpired)

        val okHttpClient = provideOkHttpClient(
            sessionInterceptor = sessionInterceptor,
            authExpiredInterceptor = authExpiredInterceptor,
            cookieJar = cookieJar,
        )

        return AnthropicApiClient(okHttpClient)
    }

    private fun provideCookieJar(context: Context): PersistentCookieJar =
        PersistentCookieJar(context)

    private fun provideOkHttpClient(
        sessionInterceptor: SessionInterceptor,
        authExpiredInterceptor: AuthExpiredInterceptor,
        cookieJar: PersistentCookieJar,
    ): okhttp3.OkHttpClient =
        okhttp3.OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(sessionInterceptor)
            .addInterceptor(authExpiredInterceptor)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()
}
