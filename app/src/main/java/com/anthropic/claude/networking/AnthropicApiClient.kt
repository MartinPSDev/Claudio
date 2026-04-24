package com.anthropic.claude.networking

import com.anthropic.claude.api.account.UpdateAccountRequest
import com.anthropic.claude.api.chat.ChatCompletionRequest
import com.anthropic.claude.api.chat.CreateChatRequest
import com.anthropic.claude.api.chat.MoveChatsRequest
import com.anthropic.claude.api.chat.RecordToolResultRequest
import com.anthropic.claude.api.chat.UpdateChatRequest
import com.anthropic.claude.api.consent.CheckConsentRequest
import com.anthropic.claude.api.consent.RevokeConsentRequest
import com.anthropic.claude.api.consent.UserConsentRequest
import com.anthropic.claude.api.login.SendMagicLinkRequest
import com.anthropic.claude.api.login.VerifyGoogleMobileRequest
import com.anthropic.claude.api.login.VerifyMagicLinkRequest
import com.anthropic.claude.api.mcp.AttachMcpPromptRequest
import com.anthropic.claude.api.mcp.CreateMcpRemoteServerRequest
import com.anthropic.claude.api.notification.NotificationChannelUpdateParams
import com.anthropic.claude.api.project.ProjectCreateParams
import com.anthropic.claude.api.project.ProjectUpdateParams
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * OkHttp-based HTTP client for the Claude API.
 * All endpoint paths are defined in [ApiEndpoints].
 */
class AnthropicApiClient(
    private val baseUrl: String = BASE_URL_PRODUCTION,
    private val sessionToken: String? = null,
) {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = false
    }

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val req = chain.request().newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("anthropic-client-platform", "android")
                .apply { sessionToken?.let { header("Cookie", "sessionKey=$it") } }
                .build()
            chain.proceed(req)
        }
        .build()

    // ── Helpers ─────────────────────────────────────────────────────────────

    private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()

    private inline fun <reified T> T.toBody(): RequestBody =
        json.encodeToString(this).toRequestBody(JSON_MEDIA_TYPE)

    private fun get(path: String): Response =
        httpClient.newCall(Request.Builder().url("$baseUrl$path").get().build()).execute()

    private fun post(path: String, body: RequestBody): Response =
        httpClient.newCall(Request.Builder().url("$baseUrl$path").post(body).build()).execute()

    private fun put(path: String, body: RequestBody): Response =
        httpClient.newCall(Request.Builder().url("$baseUrl$path").put(body).build()).execute()

    private fun patch(path: String, body: RequestBody): Response =
        httpClient.newCall(Request.Builder().url("$baseUrl$path").patch(body).build()).execute()

    private fun delete(path: String, body: RequestBody? = null): Response =
        httpClient.newCall(Request.Builder().url("$baseUrl$path").delete(body).build()).execute()

    private fun String.org(org: String) = replace("{org}", org).replace("{org_uuid}", org)

    // ── Auth ────────────────────────────────────────────────────────────────

    fun sendMagicLink(request: SendMagicLinkRequest): Response =
        post(ApiEndpoints.SEND_MAGIC_LINK, request.toBody())

    fun verifyMagicLink(request: VerifyMagicLinkRequest): Response =
        post(ApiEndpoints.VERIFY_MAGIC_LINK, request.toBody())

    fun verifyGoogleMobile(request: VerifyGoogleMobileRequest): Response =
        post(ApiEndpoints.VERIFY_GOOGLE_MOBILE, request.toBody())

    fun logout(): Response =
        post(ApiEndpoints.LOGOUT, "{}".toRequestBody(JSON_MEDIA_TYPE))

    // ── Account ─────────────────────────────────────────────────────────────

    fun getAccount(): Response = get(ApiEndpoints.ACCOUNT)

    fun updateAccount(request: UpdateAccountRequest): Response =
        patch(ApiEndpoints.ACCOUNT, request.toBody())

    fun getAppStart(): Response = get(ApiEndpoints.APP_START)

    // ── Consent ─────────────────────────────────────────────────────────────

    fun getConsents(): Response = get(ApiEndpoints.CONSENTS)

    fun checkConsent(request: CheckConsentRequest): Response =
        post(ApiEndpoints.CONSENTS_CHECK, request.toBody())

    fun grantConsent(request: UserConsentRequest): Response =
        post(ApiEndpoints.CONSENTS, request.toBody())

    fun revokeConsent(request: RevokeConsentRequest): Response =
        post(ApiEndpoints.CONSENTS_REVOKE, request.toBody())

    // ── Chat Conversations ───────────────────────────────────────────────────

    fun createChat(orgId: String, request: CreateChatRequest): Response =
        post(ApiEndpoints.CONVERSATIONS.org(orgId), request.toBody())

    fun getConversations(orgId: String): Response =
        get(ApiEndpoints.CONVERSATIONS.org(orgId))

    fun getConversation(orgId: String, chatId: String): Response =
        get(ApiEndpoints.CONVERSATION.org(orgId).replace("{chat}", chatId))

    fun updateConversation(orgId: String, chatId: String, request: UpdateChatRequest): Response =
        patch(ApiEndpoints.CONVERSATION.org(orgId).replace("{chat}", chatId), request.toBody())

    fun deleteConversation(orgId: String, chatId: String): Response =
        delete(ApiEndpoints.CONVERSATION.org(orgId).replace("{chat}", chatId))

    fun deleteManyConversations(orgId: String, request: MoveChatsRequest): Response =
        post(ApiEndpoints.CONVERSATIONS_DELETE_MANY.org(orgId), request.toBody())

    fun moveChats(orgId: String, request: MoveChatsRequest): Response =
        post(ApiEndpoints.CONVERSATIONS.org(orgId) + "/move", request.toBody())

    fun sendCompletion(orgId: String, chatId: String, request: ChatCompletionRequest): Response =
        post(ApiEndpoints.CONVERSATION_COMPLETION.org(orgId).replace("{chat}", chatId), request.toBody())

    fun retryCompletion(orgId: String, chatId: String): Response =
        post(ApiEndpoints.CONVERSATION_RETRY.org(orgId).replace("{chat}", chatId),
            "{}".toRequestBody(JSON_MEDIA_TYPE))

    fun stopResponse(orgId: String, chatId: String): Response =
        post(ApiEndpoints.CONVERSATION_STOP.org(orgId).replace("{chat}", chatId),
            "{}".toRequestBody(JSON_MEDIA_TYPE))

    fun recordToolResult(orgId: String, chatId: String, request: RecordToolResultRequest): Response =
        post(ApiEndpoints.CONVERSATION_TOOL_RESULT.org(orgId).replace("{chat}", chatId), request.toBody())

    fun searchConversations(orgId: String, query: String): Response =
        get(ApiEndpoints.CONVERSATION_SEARCH.replace("{org_uuid}", orgId) + "?q=$query")

    // ── Projects ─────────────────────────────────────────────────────────────

    fun getProjects(orgId: String): Response =
        get(ApiEndpoints.PROJECTS.replace("{org_uuid}", orgId))

    fun createProject(orgId: String, request: ProjectCreateParams): Response =
        post(ApiEndpoints.PROJECTS.replace("{org_uuid}", orgId), request.toBody())

    fun getProject(orgId: String, projectId: String): Response =
        get(ApiEndpoints.PROJECT.replace("{org_uuid}", orgId).replace("{project_uuid}", projectId))

    fun updateProject(orgId: String, projectId: String, request: ProjectUpdateParams): Response =
        patch(ApiEndpoints.PROJECT.replace("{org_uuid}", orgId).replace("{project_uuid}", projectId), request.toBody())

    fun deleteProject(orgId: String, projectId: String): Response =
        delete(ApiEndpoints.PROJECT.replace("{org_uuid}", orgId).replace("{project_uuid}", projectId))

    // ── Styles ───────────────────────────────────────────────────────────────

    fun getStyles(orgId: String): Response =
        get(ApiEndpoints.STYLES.replace("{org_uuid}", orgId))

    // ── Memory ───────────────────────────────────────────────────────────────

    fun getMemory(orgId: String): Response =
        get(ApiEndpoints.MEMORY.replace("{org_uuid}", orgId))

    fun resetMemory(orgId: String): Response =
        post(ApiEndpoints.MEMORY_RESET.replace("{org_uuid}", orgId),
            "{}".toRequestBody(JSON_MEDIA_TYPE))

    // ── Notifications ────────────────────────────────────────────────────────

    fun registerNotificationChannel(orgId: String, request: NotificationChannelUpdateParams): Response =
        put(ApiEndpoints.NOTIFICATION_CHANNELS.replace("{org_uuid}", orgId), request.toBody())

    fun getNotificationPreferences(orgId: String): Response =
        get(ApiEndpoints.NOTIFICATION_PREFERENCES.replace("{org_uuid}", orgId))

    // ── MCP ──────────────────────────────────────────────────────────────────

    fun createMcpRemoteServer(request: CreateMcpRemoteServerRequest): Response =
        post("/api/mcp/servers", request.toBody())

    fun attachMcpPrompt(orgId: String, chatId: String, request: AttachMcpPromptRequest): Response =
        post("/api/organizations/$orgId/chat_conversations/$chatId/mcp_prompt", request.toBody())

    // ── Experiences ──────────────────────────────────────────────────────────

    fun getExperiences(): Response = get(ApiEndpoints.EXPERIENCES)

    companion object {
        const val BASE_URL_PRODUCTION = "https://claude.ai"
        const val BASE_URL_STAGING    = "https://claude-ai.staging.ant.dev"
    }
}
