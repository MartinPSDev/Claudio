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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

/**
 * OkHttp-based HTTP client for the Claude API.
 * All endpoint paths are defined in [ApiEndpoints].
 */
class AnthropicApiClient(
    private val baseUrl: String = BASE_URL_PRODUCTION,
    val httpClient: OkHttpClient,
) {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = false
    }

    private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()

    private inline fun <reified T> T.toBody(): RequestBody =
        json.encodeToString(this).toRequestBody(JSON_MEDIA_TYPE)

    private suspend fun executeRequest(request: Request): Response = withContext(Dispatchers.IO) {
        httpClient.newCall(request).execute()
    }

    private suspend fun get(path: String, orgId: String? = null): Response {
        val req = Request.Builder().url("$baseUrl$path").get()
        orgId?.let { req.header("x-organization-uuid", it) }
        return executeRequest(req.build())
    }

    private suspend fun post(path: String, body: RequestBody, orgId: String? = null): Response {
        val req = Request.Builder().url("$baseUrl$path").post(body)
        orgId?.let { req.header("x-organization-uuid", it) }
        return executeRequest(req.build())
    }

    private suspend fun put(path: String, body: RequestBody, orgId: String? = null): Response {
        val req = Request.Builder().url("$baseUrl$path").put(body)
        orgId?.let { req.header("x-organization-uuid", it) }
        return executeRequest(req.build())
    }

    private suspend fun patch(path: String, body: RequestBody, orgId: String? = null): Response {
        val req = Request.Builder().url("$baseUrl$path").patch(body)
        orgId?.let { req.header("x-organization-uuid", it) }
        return executeRequest(req.build())
    }

    private suspend fun delete(path: String, body: RequestBody? = null, orgId: String? = null): Response {
        val req = Request.Builder().url("$baseUrl$path").delete(body)
        orgId?.let { req.header("x-organization-uuid", it) }
        return executeRequest(req.build())
    }

    private fun String.org(org: String) = replace("{org}", org).replace("{org_uuid}", org)

    // ── Auth ────────────────────────────────────────────────────────────────

    suspend fun sendMagicLink(request: SendMagicLinkRequest): Response =
        post(ApiEndpoints.SEND_MAGIC_LINK, request.toBody())

    suspend fun verifyMagicLink(request: VerifyMagicLinkRequest): Response =
        post(ApiEndpoints.VERIFY_MAGIC_LINK, request.toBody())

    suspend fun verifyGoogleMobile(request: VerifyGoogleMobileRequest): Response =
        post(ApiEndpoints.VERIFY_GOOGLE_MOBILE, request.toBody())

    suspend fun logout(): Response =
        post(ApiEndpoints.LOGOUT, "{}".toRequestBody(JSON_MEDIA_TYPE))

    // ── Account ─────────────────────────────────────────────────────────────

    suspend fun getAccount(): Response = get(ApiEndpoints.ACCOUNT)

    suspend fun updateAccount(request: UpdateAccountRequest): Response =
        patch(ApiEndpoints.ACCOUNT, request.toBody())

    suspend fun getAppStart(): Response = get(ApiEndpoints.APP_START)

    // ── Consent ─────────────────────────────────────────────────────────────

    suspend fun getConsents(): Response = get(ApiEndpoints.CONSENTS)

    suspend fun checkConsent(request: CheckConsentRequest): Response =
        post(ApiEndpoints.CONSENTS_CHECK, request.toBody())

    suspend fun grantConsent(request: UserConsentRequest): Response =
        post(ApiEndpoints.CONSENTS, request.toBody())

    suspend fun revokeConsent(request: RevokeConsentRequest): Response =
        post(ApiEndpoints.CONSENTS_REVOKE, request.toBody())

    // ── Chat Conversations ───────────────────────────────────────────────────

    suspend fun createChat(orgId: String, request: CreateChatRequest): Response =
        post(ApiEndpoints.CONVERSATIONS.org(orgId), request.toBody(), orgId)

    suspend fun getConversations(orgId: String): Response =
        get(ApiEndpoints.CONVERSATIONS.org(orgId), orgId)

    suspend fun getConversation(orgId: String, chatId: String): Response =
        get(ApiEndpoints.CONVERSATION.org(orgId).replace("{chat}", chatId), orgId)

    suspend fun updateConversation(orgId: String, chatId: String, request: UpdateChatRequest): Response =
        patch(ApiEndpoints.CONVERSATION.org(orgId).replace("{chat}", chatId), request.toBody(), orgId)

    suspend fun deleteConversation(orgId: String, chatId: String): Response =
        delete(ApiEndpoints.CONVERSATION.org(orgId).replace("{chat}", chatId), orgId = orgId)

    suspend fun deleteManyConversations(orgId: String, request: MoveChatsRequest): Response =
        post(ApiEndpoints.CONVERSATIONS_DELETE_MANY.org(orgId), request.toBody(), orgId)

    suspend fun moveChats(orgId: String, request: MoveChatsRequest): Response =
        post(ApiEndpoints.CONVERSATIONS.org(orgId) + "/move", request.toBody(), orgId)

    suspend fun sendCompletion(orgId: String, chatId: String, request: ChatCompletionRequest): Response =
        post(ApiEndpoints.CONVERSATION_COMPLETION.org(orgId).replace("{chat}", chatId), request.toBody(), orgId)

    suspend fun retryCompletion(orgId: String, chatId: String): Response =
        post(ApiEndpoints.CONVERSATION_RETRY.org(orgId).replace("{chat}", chatId),
            "{}".toRequestBody(JSON_MEDIA_TYPE), orgId)

    suspend fun stopResponse(orgId: String, chatId: String): Response =
        post(ApiEndpoints.CONVERSATION_STOP.org(orgId).replace("{chat}", chatId),
            "{}".toRequestBody(JSON_MEDIA_TYPE), orgId)

    suspend fun recordToolResult(orgId: String, chatId: String, request: RecordToolResultRequest): Response =
        post(ApiEndpoints.CONVERSATION_TOOL_RESULT.org(orgId).replace("{chat}", chatId), request.toBody(), orgId)

    suspend fun searchConversations(orgId: String, query: String): Response =
        get(ApiEndpoints.CONVERSATION_SEARCH.replace("{org_uuid}", orgId) + "?q=$query", orgId)

    // ── Projects ─────────────────────────────────────────────────────────────

    suspend fun getProjects(orgId: String): Response =
        get(ApiEndpoints.PROJECTS.replace("{org_uuid}", orgId), orgId)

    suspend fun createProject(orgId: String, request: ProjectCreateParams): Response =
        post(ApiEndpoints.PROJECTS.replace("{org_uuid}", orgId), request.toBody(), orgId)

    suspend fun getProject(orgId: String, projectId: String): Response =
        get(ApiEndpoints.PROJECT.replace("{org_uuid}", orgId).replace("{project_uuid}", projectId), orgId)

    suspend fun updateProject(orgId: String, projectId: String, request: ProjectUpdateParams): Response =
        patch(ApiEndpoints.PROJECT.replace("{org_uuid}", orgId).replace("{project_uuid}", projectId), request.toBody(), orgId)

    suspend fun deleteProject(orgId: String, projectId: String): Response =
        delete(ApiEndpoints.PROJECT.replace("{org_uuid}", orgId).replace("{project_uuid}", projectId), orgId = orgId)

    // ── Styles ───────────────────────────────────────────────────────────────

    suspend fun getStyles(orgId: String): Response =
        get(ApiEndpoints.STYLES.replace("{org_uuid}", orgId), orgId)

    // ── Memory ───────────────────────────────────────────────────────────────

    suspend fun getMemory(orgId: String): Response =
        get(ApiEndpoints.MEMORY.replace("{org_uuid}", orgId), orgId)

    suspend fun resetMemory(orgId: String): Response =
        post(ApiEndpoints.MEMORY_RESET.replace("{org_uuid}", orgId),
            "{}".toRequestBody(JSON_MEDIA_TYPE), orgId)

    // ── Notifications ────────────────────────────────────────────────────────

    suspend fun registerNotificationChannel(orgId: String, request: NotificationChannelUpdateParams): Response =
        put(ApiEndpoints.NOTIFICATION_CHANNELS.replace("{org_uuid}", orgId), request.toBody(), orgId)

    suspend fun getNotificationPreferences(orgId: String): Response =
        get(ApiEndpoints.NOTIFICATION_PREFERENCES.replace("{org_uuid}", orgId), orgId)

    // ── MCP ──────────────────────────────────────────────────────────────────

    suspend fun createMcpRemoteServer(request: CreateMcpRemoteServerRequest): Response =
        post("/api/mcp/servers", request.toBody())

    suspend fun attachMcpPrompt(orgId: String, chatId: String, request: AttachMcpPromptRequest): Response =
        post("/api/organizations/$orgId/chat_conversations/$chatId/mcp_prompt", request.toBody(), orgId)

    // ── Experiences ──────────────────────────────────────────────────────────

    suspend fun getExperiences(): Response = get(ApiEndpoints.EXPERIENCES)

    companion object {
        const val BASE_URL_PRODUCTION = "https://claude.ai"
        const val BASE_URL_STAGING    = "https://claude-ai.staging.ant.dev"
    }
}

