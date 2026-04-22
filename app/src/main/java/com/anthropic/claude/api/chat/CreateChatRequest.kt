package com.anthropic.claude.api.chat

import com.anthropic.claude.types.strings.ChatId
import com.anthropic.claude.types.strings.ModelId
import com.anthropic.claude.types.strings.ProjectId
import com.anthropic.claude.types.strings.ResearchMode
import com.anthropic.claude.types.strings.ThinkingMode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(
    val uuid: ChatId,
    val name: String,
    val summary: String? = null,
    val model: ModelId? = null,
    @SerialName("project_uuid")
    val projectUuid: ProjectId? = null,
    @SerialName("paprika_mode")
    val paprikaMode: ThinkingMode? = null,
    @SerialName("compass_mode")
    val compassMode: ResearchMode? = null,
    @SerialName("is_temporary")
    val isTemporary: Boolean = false,
    @SerialName("include_conversation_preferences")
    val includeConversationPreferences: Boolean? = null,
    @SerialName("orbit_action_uuid")
    val orbitActionUuid: String? = null,
)
