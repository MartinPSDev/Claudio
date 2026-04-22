package com.anthropic.claude.chatlist.all.overlays

import kotlinx.serialization.Serializable

sealed interface AllChatsListOverlay {

    @Serializable
    data object None : AllChatsListOverlay

    @Serializable
    data object CreateProject : AllChatsListOverlay
}
