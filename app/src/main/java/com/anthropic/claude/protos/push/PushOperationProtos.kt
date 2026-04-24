package com.anthropic.claude.protos.push

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire-proto: push notification to open a code/agent session.
 * proto file: anthropic/claude/push/operations.proto
 */
@Serializable
data class OpenCodeSessionRequest(
    @SerialName("session_id")        val sessionId: String? = null,
    @SerialName("account_uuid")      val accountUuid: String? = null,
    @SerialName("organization_uuid") val organizationUuid: String? = null,
)

/**
 * Wire-proto: push notification to open a dispatch (agent) session.
 * proto file: anthropic/claude/push/operations.proto
 */
@Serializable
data class OpenDispatchRequest(
    @SerialName("session_id")        val sessionId: String? = null,
    @SerialName("account_uuid")      val accountUuid: String? = null,
    @SerialName("organization_uuid") val organizationUuid: String? = null,
)

/**
 * Wire-proto: push notification to open an Orbit (assistant) conversation.
 * proto file: anthropic/claude/push/operations.proto
 */
@Serializable
data class OpenOrbitRequest(
    @SerialName("conversation_uuid") val conversationUuid: String? = null,
    @SerialName("account_uuid")      val accountUuid: String? = null,
    @SerialName("organization_uuid") val organizationUuid: String? = null,
)

/**
 * Wire-proto: outer push-notification envelope containing the target service/method and payload.
 * proto file: anthropic/claude/push/envelope.proto
 */
@Serializable
data class PushOperationEnvelope(
    val service: String? = null,
    val method: String? = null,
    val request: ByteArray? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PushOperationEnvelope) return false
        return service == other.service && method == other.method && request.contentEquals(other.request ?: ByteArray(0))
    }
    override fun hashCode(): Int = arrayOf(service, method, request?.contentHashCode()).contentHashCode()
}
