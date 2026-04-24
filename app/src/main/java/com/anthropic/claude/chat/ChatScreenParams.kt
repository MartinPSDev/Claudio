package com.anthropic.claude.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Parameters for navigating to the chat screen.
 */
@Serializable
data class ChatScreenParams(
    @SerialName("chatId")                   val chatId: String? = null,
    @SerialName("initialPrompt")            val initialPrompt: String? = null,
    @SerialName("initialModel")             val initialModel: String? = null,
    @SerialName("projectId")               val projectId: String? = null,
    @SerialName("isRootScreen")            val isRootScreen: Boolean? = null,
    @SerialName("createdAsNewChat")        val createdAsNewChat: Boolean? = null,
    @SerialName("artifactIdentifierToOpen") val artifactIdentifierToOpen: String? = null,
    @SerialName("orbitActionUuid")          val orbitActionUuid: String? = null,
)
