package com.anthropic.claude.mcpapps.transport

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/** JSON-RPC 2.0 request identifier (can be a String or a Number). */
typealias RequestId = JsonPrimitive

@Serializable
data class JsonRpcRequest(
    val jsonrpc: String = "2.0",
    val id: RequestId,
    val method: String,
    val params: JsonObject? = null,
)

@Serializable
data class JsonRpcError(
    val code: Int,
    val message: String,
    val data: JsonObject? = null,
)

@Serializable
data class JsonRpcResponse(
    val jsonrpc: String = "2.0",
    val id: RequestId,
    val result: JsonObject? = null,
    val error: JsonRpcError? = null,
)
