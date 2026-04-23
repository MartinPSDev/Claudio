package com.anthropic.claude.protos.push

/**
 * Protobuf message sent to wake up a Conway (real-time WebSocket) session.
 * Transmitted via the Wire/gRPC push notification channel.
 *
 * Proto field tags:
 *  - tag 1: account_uuid
 *  - tag 2: organization_uuid
 *  - tag 3: conway_session_id
 */
data class ConwayWakeRequest(
    val accountUuid: String = "",
    val organizationUuid: String = "",
    val conwaySessionId: String = "",
) {
    companion object {
        const val PROTO_NAME = "ConwayWakeRequest"
    }
}
