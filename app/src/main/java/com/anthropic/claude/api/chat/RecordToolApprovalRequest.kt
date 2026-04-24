package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Request to record user approval/denial of a tool-use action. */
@Serializable
data class RecordToolApprovalRequest(
    @SerialName("tool_use_id")    val toolUseId: String? = null,
    @SerialName("is_approved")    val isApproved: Boolean? = null,
    @SerialName("approval_key")   val approvalKey: String? = null,
    @SerialName("approval_option") val approvalOption: String? = null,
    val params: JsonElement? = null,
)
