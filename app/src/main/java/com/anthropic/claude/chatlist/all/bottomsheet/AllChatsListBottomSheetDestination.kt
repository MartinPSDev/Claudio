package com.anthropic.claude.chatlist.all.bottomsheet

import kotlinx.serialization.Serializable

sealed interface AllChatsListBottomSheetDestination {

    @Serializable
    data object Closed : AllChatsListBottomSheetDestination

    @Serializable
    data object SelectProject : AllChatsListBottomSheetDestination
}
