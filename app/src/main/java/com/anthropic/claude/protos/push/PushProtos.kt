package com.anthropic.claude.protos.push

import com.squareup.wire.ProtoField

/**
 * Wire proto message for opening a remote code session from a push notification.
 */
data class OpenCodeSessionRequest(
    @ProtoField(tag = 1) val accountUuid: String? = null,
    @ProtoField(tag = 2) val organizationUuid: String? = null,
    @ProtoField(tag = 3) val sessionId: String? = null,
)

/**
 * Wire proto envelope wrapping a push operation for background RPC dispatch.
 */
data class PushOperationEnvelope(
    @ProtoField(tag = 1) val method: String? = null,
    @ProtoField(tag = 2) val service: String? = null,
    @ProtoField(tag = 3) val request: ByteArray? = null,
)
