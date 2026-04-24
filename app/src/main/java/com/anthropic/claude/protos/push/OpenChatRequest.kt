package com.anthropic.claude.protos.push

import com.squareup.wire.Message
import com.squareup.wire.ProtoField

/**
 * Wire proto message for opening a specific chat conversation from a push notification.
 */
data class OpenChatRequest(
    @ProtoField(tag = 1) val accountUuid: String? = null,
    @ProtoField(tag = 2) val organizationUuid: String? = null,
    @ProtoField(tag = 3) val conversationUuid: String? = null,
    @ProtoField(tag = 4) val messageUuid: String? = null,
    @ProtoField(tag = 5) val samplingCompletedTimestamp: Long? = null,
)
