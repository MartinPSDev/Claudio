package com.anthropic.claude.api.tasks

import com.anthropic.claude.types.strings.ModelId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Details about a tool call that is blocking a task and requires user approval.
 */
@Serializable
data class BlockingToolDetail(
    @SerialName("tool_use_id") val toolUseId: String,
    val name: String,
    val input: JsonObject? = null,
    val output: JsonObject? = null,
)

/**
 * Optional model/system overrides sent when approving a task agent to proceed.
 */
@Serializable
data class ApproveTaskAgentOverrides(
    val model: ModelId? = null,
    val speed: String? = null,
    val system: String? = null,
) {
    /** Returns true when no overrides are set — agent uses its own defaults. */
    val isEmpty: Boolean
        get() = model == null && speed == null && system == null
}
