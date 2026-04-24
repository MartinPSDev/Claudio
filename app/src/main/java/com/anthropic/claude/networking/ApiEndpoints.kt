package com.anthropic.claude.networking

/**
 * All known API endpoint paths extracted from the Claude Android APK.
 * Base URL: https://claude.ai
 */
object ApiEndpoints {

    // ── Auth ────────────────────────────────────────────────────────────────
    const val SEND_MAGIC_LINK            = "/api/auth/send_magic_link"
    const val VERIFY_MAGIC_LINK          = "/api/auth/verify_magic_link"
    const val VERIFY_GOOGLE_MOBILE       = "/api/auth/verify_google_mobile"
    const val SEND_PHONE_CODE            = "/api/auth/send_phone_code"
    const val VERIFY_PHONE_CODE          = "/api/auth/verify_phone_code"
    const val TRUSTED_DEVICES            = "/api/auth/trusted_devices"
    const val LOGOUT                     = "/api/auth/logout"

    // ── Account ─────────────────────────────────────────────────────────────
    const val ACCOUNT                    = "/api/account"
    const val APP_START                  = "/api/app_start"
    const val BOOTSTRAP                  = "/api/bootstrap"

    // ── Consent ─────────────────────────────────────────────────────────────
    const val CONSENTS                   = "/api/accounts/me/consents"
    const val CONSENTS_CHECK             = "/api/accounts/me/consents/check"
    const val CONSENTS_REVOKE            = "/api/accounts/me/consents/revoke"

    // ── Chat Conversations ───────────────────────────────────────────────────
    const val CONVERSATIONS              = "/api/organizations/{org}/chat_conversations"
    const val CONVERSATION               = "/api/organizations/{org}/chat_conversations/{chat}"
    const val CONVERSATION_COMPLETION    = "/api/organizations/{org}/chat_conversations/{chat}/completion"
    const val CONVERSATION_RETRY         = "/api/organizations/{org}/chat_conversations/{chat}/retry_completion"
    const val CONVERSATION_STOP          = "/api/organizations/{org}/chat_conversations/{chat}/stop_response"
    const val CONVERSATION_TITLE         = "/api/organizations/{org}/chat_conversations/{chat}/title"
    const val CONVERSATION_SHARE         = "/api/organizations/{org}/chat_conversations/{chat}/share"
    const val CONVERSATION_SHARES        = "/api/organizations/{org}/chat_conversations/{chat}/shares"
    const val CONVERSATION_TOOL_APPROVAL = "/api/organizations/{org}/chat_conversations/{chat}/tool_approval"
    const val CONVERSATION_TOOL_RESULT   = "/api/organizations/{org}/chat_conversations/{chat}/tool_result"
    const val CONVERSATION_MODEL_FALLBACK= "/api/organizations/{org}/chat_conversations/{chat}/model_fallback"
    const val CONVERSATIONS_DELETE_MANY  = "/api/organizations/{org}/chat_conversations/delete_many"
    const val CONVERSATION_SEARCH        = "/api/organizations/{org_uuid}/conversation/search"
    const val MESSAGE_FEEDBACK           = "/api/organizations/{org}/chat_conversations/{chat}/chat_messages/{msg}/chat_feedback"
    const val MESSAGE_FLAGS              = "/api/organizations/{org_uuid}/chat_conversations/{chat_uuid}/chat_messages/{msg}/flags"
    const val TASK_MOBILE_STATUS         = "/api/organizations/{org_uuid}/chat_conversations/{chat_uuid}/task/{task_id}/mobile_status"
    const val TASK_STOP                  = "/api/organizations/{org_uuid}/chat_conversations/{chat_uuid}/task/{task_id}/stop"

    // ── File Upload (Wiggle) ─────────────────────────────────────────────────
    const val FILES_PREPARE_UPLOAD       = "/api/organizations/{org_uuid}/conversations/{conv_uuid}/files/prepare-upload"
    const val FILES_UPLOAD               = "/api/organizations/{org_uuid}/conversations/{conv_uuid}/wiggle/upload-file"
    const val FILES_DELETE               = "/api/organizations/{org_uuid}/conversations/{conv_uuid}/wiggle/delete-file"
    const val FILES_CONVERT_ARTIFACT     = "/api/organizations/{org_uuid}/conversations/{conv_uuid}/wiggle/convert-file-to-artifact"

    // ── Artifacts ───────────────────────────────────────────────────────────
    const val ARTIFACT_VERSIONS          = "/api/organizations/{org}/artifacts/{conv_uuid}/versions"
    const val ARTIFACT_VISIBILITY        = "/api/organizations/{org}/artifact-versions/{artifact_id}/visibility"

    // ── Projects ─────────────────────────────────────────────────────────────
    const val PROJECTS                   = "/api/organizations/{org_uuid}/projects"
    const val PROJECTS_V2                = "/api/organizations/{org_uuid}/projects_v2"
    const val PROJECT                    = "/api/organizations/{org_uuid}/projects/{project_uuid}"
    const val PROJECT_CONVERSATIONS      = "/api/organizations/{org_uuid}/projects/{project_uuid}/conversations"
    const val PROJECT_DOCS               = "/api/organizations/{org_uuid}/projects/{project_uuid}/docs"
    const val PROJECT_DOC                = "/api/organizations/{org_uuid}/projects/{project_uuid}/docs/{doc_uuid}"
    const val PROJECT_FILES_DELETE_MANY  = "/api/organizations/{org_uuid}/projects/{project_uuid}/files/delete_many"
    const val PROJECT_UPLOAD             = "/api/organizations/{org_uuid}/projects/{project_uuid}/upload"
    const val PROJECT_KB_RESYNC          = "/api/organizations/{org_uuid}/projects/{project_uuid}/kb/resync"
    const val PROJECT_KB_STATS           = "/api/organizations/{org_uuid}/projects/{project_uuid}/kb/stats"

    // ── Styles ───────────────────────────────────────────────────────────────
    const val STYLES                     = "/api/organizations/{org_uuid}/list_styles"

    // ── Memory ───────────────────────────────────────────────────────────────
    const val MEMORY                     = "/api/organizations/{org_uuid}/memory"
    const val MEMORY_RESET               = "/api/organizations/{org_uuid}/memory/reset"

    // ── Notifications ────────────────────────────────────────────────────────
    const val NOTIFICATION_CHANNELS      = "/api/organizations/{org_uuid}/notification/channels"
    const val NOTIFICATION_PREFERENCES  = "/api/organizations/{org_uuid}/notification/preferences"
    const val NOTIFICATION_TRACK_OPEN   = "/api/organizations/{org_uuid}/notification/push/track_open"
    const val NOTIFICATION_DEBUG_PUSH   = "/api/organizations/{org_uuid}/notification/debug/test_push"

    // ── Orbit ────────────────────────────────────────────────────────────────
    const val ORBIT_BRIEFINGS            = "/api/organizations/{org_uuid}/orbit/briefings"
    const val ORBIT_ACTION               = "/api/organizations/{org_uuid}/orbit/actions/{action_uuid}"
    const val ORBIT_INSIGHT_FEEDBACK     = "/api/organizations/{org_uuid}/orbit/insights/{insight_uuid}/feedback"

    // ── Tasks ────────────────────────────────────────────────────────────────
    const val TASKS                      = "/api/organizations/{org_uuid}/tasks"
    const val TASK                       = "/api/organizations/{org_uuid}/tasks/{task_uuid}"
    const val TASK_APPROVE               = "/api/organizations/{org_uuid}/tasks/{task_uuid}/approve"
    const val TASK_EVENTS                = "/api/organizations/{org_uuid}/tasks/{task_uuid}/events"
    const val TASK_EVENTS_STREAM         = "/api/organizations/{org_uuid}/tasks/{task_uuid}/events/stream"
    const val TASK_MESSAGE               = "/api/organizations/{org_uuid}/tasks/{task_uuid}/message"
    const val TASK_STREAM                = "/api/organizations/{org_uuid}/tasks/{task_uuid}/stream"

    // ── Sessions (Remote Code) ───────────────────────────────────────────────
    const val SESSION_FILL_SENSITIVE     = "/api/organizations/{org_uuid}/sessions/{session_id}/browser_sessions/current/fill_sensitive_text"

    // ── MCP ──────────────────────────────────────────────────────────────────
    const val MCP_AUTH_CALLBACK          = "/api/mcp/auth_callback"

    // ── Feedback ─────────────────────────────────────────────────────────────
    const val APP_FEEDBACK               = "/api/organizations/{org}/app_feedback"

    // ── Experiences ──────────────────────────────────────────────────────────
    const val EXPERIENCES                = "/api/experiences"
}
