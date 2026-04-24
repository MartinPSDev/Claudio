package com.anthropic.claude.protos.push

/**
 * Push notification dispatch service interface.
 * Handles routing of incoming push messages to the correct screen/session.
 */
interface LoggedInPushOperationsService {
    suspend fun conwayWake(request: ConwayWakeRequest): Any?
    suspend fun openChat(request: OpenChatRequest): Any?
    suspend fun openCodeSession(request: OpenCodeSessionRequest): Any?
    suspend fun openDispatch(request: OpenDispatchRequest): Any?
    suspend fun openOrbit(request: OpenOrbitRequest): Any?
}
