package com.anthropic.claude.protos.push

import com.squareup.wire.ProtoField

/**
 * Wire proto for opening a dispatch (task/agent) session from a push notification.
 */
data class OpenDispatchRequest(
    @ProtoField(tag = 1) val accountUuid: String? = null,
    @ProtoField(tag = 2) val organizationUuid: String? = null,
    @ProtoField(tag = 3) val sessionId: String? = null,
)

/**
 * Wire proto for opening an orbit conversation from a push notification.
 */
data class OpenOrbitRequest(
    @ProtoField(tag = 1) val accountUuid: String? = null,
    @ProtoField(tag = 2) val organizationUuid: String? = null,
    @ProtoField(tag = 3) val conversationUuid: String? = null,
)
