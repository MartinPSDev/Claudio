package com.anthropic.claude.chat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthropic.claude.analytics.events.ChatEvents
import com.anthropic.claude.analytics.events.MobileAppUseEvents
import com.anthropic.claude.api.chat.ChatCompletionRequest
import com.anthropic.claude.api.chat.ChatConversation
import com.anthropic.claude.api.chat.ChatMessage
import com.anthropic.claude.api.chat.CreateChatRequest
import com.anthropic.claude.api.chat.InputMode
import com.anthropic.claude.api.mcp.McpServer
import com.anthropic.claude.api.mcp.McpToolApprovalOption
import com.anthropic.claude.artifact.model.ArtifactMetadata
import com.anthropic.claude.artifact.sheet.ArtifactBottomSheetParams
import com.anthropic.claude.chat.parse.ParsedContentBlock
import com.anthropic.claude.chat.parse.ParsedContentBlockId
import com.anthropic.claude.chat.parse.SseEventProcessor
import com.anthropic.claude.chat.repository.ChatRepository
import com.anthropic.claude.configs.flags.SendRetryConfig
import com.anthropic.claude.tool.model.KnowledgeSource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

/**
 * Primary ViewModel governing the entire chat screen lifecycle.
 * Orchestrates message sending, SSE streaming, retry logic, MCP tool
 * invocations, artifact management, polling recovery, and analytics tracking.
 */
class ChatViewModel(
    val screenParams: ChatScreenParams,
    val context: Context,

    val chatRepository: ChatRepository,
    val analyticsTracker: Any,
    val conversationRepository: Any,
    val messageRepository: Any,
    val modelConfigProvider: Any,
    val featureFlagProvider: Any,
    val projectRepository: Any,

    val chatParser: Any,
    val chatFormatter: Any,
    val mcpManager: Any,
    val toolDisplayManager: Any,

    val artifactRepository: Any,
    val artifactSheetManager: Any,

    val rateLimitManager: Any,
    val thinkingModeManager: Any,
    val compactionManager: Any,
    val notificationManager: Any,
    val searchRepository: Any,
    val organizationRepository: Any,

    val knowledgeSourceManager: Any,
    val draftManager: Any,
    val messageQueueWorker: Any,
    val sendRetryManager: Any,
    val pollingRecoveryManager: Any,
    val sseConnectionManager: Any,
    val imageUploadManager: Any,

    val chatScreenDataProvider: Any,
    val permissionManager: Any,
    val wiggleManager: Any,
    val researchModeManager: Any,
    val pushNotificationManager: Any,
    val mcpAppManager: Any,

    val aiTasksNavigator: Any,
    val speechInputManager: Any,
    val exportManager: Any,

    val sseEventProcessor: SseEventProcessor,
    val messageMetadataManager: Any,
    val toolInvocationHandler: Any,

    val chatFeedbackManager: Any,
    val sseStreamManager: Any,
    val messageAttachmentManager: Any,
    val chatTitleManager: Any,
    val organizationId: String,
    val networkMonitor: Any,
    val authConnector: Any,
    val mcpWebViewCacheManager: Any,
) : ViewModel() {

    private val _isSending = MutableStateFlow(false)
    val isSending: StateFlow<Boolean> = _isSending.asStateFlow()

    private var activeStreamJob: Job? = null

    /**
     * Send a new message in the chat.
     */
    fun sendMessage(
        inputMode: InputMode,
        text: String,
        imageAttachments: List<Any>,
        fileAttachments: List<Any>
    ) {
        if (_isSending.value) return

        val request = CreateChatRequest(
            prompt = text,
            organization_uuid = organizationId,
            timezone = android.icu.util.TimeZone.getDefault().id,
            // Attachments mapping would happen here
        )

        _isSending.value = true

        activeStreamJob = viewModelScope.launch {
            chatRepository.createChat(request)
                .catch { e ->
                    handleSendError(e, 0, SendRetryConfig())
                }
                .onCompletion {
                    _isSending.value = false
                }
                .collect { event ->
                    sseEventProcessor.processEvent(event)
                }
        }
    }

    /**
     * Send message to an existing chat conversation.
     */
    fun sendMessageToExistingChat(
        text: String,
        imageAttachments: List<Any>,
        fileAttachments: List<Any>,
        inputMode: InputMode,
        modelId: String,
        retryReason: ChatEvents.RetryCompletionReason?,
        request: CreateChatRequest?
    ) {
        if (_isSending.value) return
        
        val chatId = getChatId()
        if (chatId.isEmpty()) return

        val completionRequest = ChatCompletionRequest(
            prompt = text,
            timezone = android.icu.util.TimeZone.getDefault().id,
            organization_uuid = organizationId
        )

        _isSending.value = true

        activeStreamJob = viewModelScope.launch {
            chatRepository.sendMessage(chatId, completionRequest)
                .catch { e ->
                    handleSendError(e, 0, SendRetryConfig())
                }
                .onCompletion {
                    _isSending.value = false
                }
                .collect { event ->
                    sseEventProcessor.processEvent(event)
                }
        }
    }

    /**
     * Cancel the current SSE stream.
     */
    fun cancelCurrentStream() {
        activeStreamJob?.cancel()
        activeStreamJob = null
        _isSending.value = false
    }

    fun retryMessage(
        text: String,
        imageAttachments: List<Any>,
        fileAttachments: List<Any>,
        inputMode: InputMode,
        modelId: String,
        reason: ChatEvents.RetryCompletionReason,
        attemptCount: Int
    ) { }

    fun getChatConversation(): ChatConversation? = null

    fun deleteMessage(messageId: String) { }

    fun updateMessageFeedback(messageId: String, chatId: String) { }

    fun findMessageByUuid(uuid: String): ChatMessage? = null

    fun getMcpServer(serverId: Any): McpServer? = null

    fun getMcpServerName(server: McpServer, toolName: String): String = ""

    fun handleMcpToolApproval(
        toolUseId: String,
        approved: Boolean,
        messageId: String,
        approvalOption: McpToolApprovalOption
    ) { }

    fun getMcpToolInvocation(blockId: ParsedContentBlockId): ParsedContentBlock.McpToolInvocation? = null

    fun reportToolResult(toolUseId: String, result: Any, messageId: String) { }

    fun getArtifactSheetParams(metadata: ArtifactMetadata): ArtifactBottomSheetParams.InMessageArtifact? = null

    fun canOpenArtifactSheet(params: ArtifactBottomSheetParams): Boolean = false

    fun getArtifactsForSheet(params: ArtifactBottomSheetParams): List<Any> = emptyList()

    fun isFeatureEnabled(featureFlag: String): Boolean = false

    fun isModelAvailable(modelId: String): Boolean = true

    suspend fun handleSendError(
        error: Throwable,
        attemptCount: Int,
        retryConfig: SendRetryConfig
    ): Any = Unit

    suspend fun startPollingRecovery(
        reason: ChatEvents.PollingRecoveryTriggerReason,
        staleThresholdMs: Long?
    ): Any = Unit

    fun retryCompletion(reason: ChatEvents.RetryCompletionReason) { }

    fun reportCompactionOutcome(
        outcome: ChatEvents.CompactionOutcome,
        chatId: String
    ) { }

    fun getPermissionCategory(permission: String): MobileAppUseEvents.MobileAppUsePermissionCategory? = null

    fun setThinkingBudget(budget: Int) { }

    fun handleKnowledgeSource(source: KnowledgeSource, enabled: Boolean) { }

    fun handlePermissionResult(
        permission: String,
        action: String,
        source: String,
        state: String,
        granted: Boolean
    ) { }

    fun onDestroy() {
        cancelCurrentStream()
    }

    fun isStreamActive(): Boolean = _isSending.value
    fun canEditMessage(): Boolean = false
    fun isResearchMode(): Boolean = false
    fun hasNotificationPermission(): Boolean = false
    fun isMcpEnabled(): Boolean = false
    fun isThinkingEnabled(): Boolean = false
    fun isWiggleEnabled(): Boolean = false
    fun isMessageQueueEnabled(): Boolean = false
    fun isSendRetryEnabled(): Boolean = false

    fun getChatId(): String = ""
    fun getCurrentModelId(): String = ""
    fun getOrganizationId(): String = organizationId
    fun getChatScreenData(): Any? = null
    fun getChatScreenDataProvider(): Any? = null
    fun getCurrentDraft(): Any? = null
    fun getActiveStreamJob(): Job? = activeStreamJob
    fun getMcpAppState(): Any? = null
}
