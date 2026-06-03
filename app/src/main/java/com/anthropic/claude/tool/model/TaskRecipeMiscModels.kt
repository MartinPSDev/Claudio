package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// =========================================================================
// Task List / Task Status Models
// =========================================================================

/**
 * Output for the TaskList tool.
 *
 * Contains a list of tasks with their steps and statuses.
 */
@Serializable
data class TaskListOutput(
    val tasks: List<TaskListItem>
)

/**
 * A task item in the task list.
 */
@Serializable
data class TaskListItem(
    val task_id: String? = null,
    val title: String? = null,
    val status: TaskItemStatus? = null,
    val steps: List<TaskListStepItem>? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)

/**
 * Status of a task.
 */
@Serializable
enum class TaskItemStatus {
    @SerialName("pending") PENDING,
    @SerialName("in_progress") IN_PROGRESS,
    @SerialName("completed") COMPLETED,
    @SerialName("failed") FAILED,
    @SerialName("cancelled") CANCELLED
}

/**
 * A step within a task list item.
 */
@Serializable
data class TaskListStepItem(
    val description: String? = null,
    val status: TaskStepItemStatus? = null,
    val tool_name: String? = null,
    val result: String? = null
)

/**
 * Status of a task step.
 */
@Serializable
enum class TaskStepItemStatus {
    @SerialName("pending") PENDING,
    @SerialName("in_progress") IN_PROGRESS,
    @SerialName("completed") COMPLETED,
    @SerialName("failed") FAILED,
    @SerialName("skipped") SKIPPED
}

// =========================================================================
// Task Propose (detailed)
// =========================================================================

/**
 * Detailed task propose input step.
 */
@Serializable
data class TaskProposeInputStep(
    val description: String? = null,
    val tool_name: String? = null,
    val tool_input: JsonElement? = null,
    val requires_approval: Boolean? = null
)

/**
 * Task propose output status.
 */
@Serializable
enum class TaskProposeOutputStatus {
    @SerialName("approved") APPROVED,
    @SerialName("rejected") REJECTED,
    @SerialName("pending") PENDING,
    @SerialName("modified") MODIFIED
}

/**
 * Task propose output step item.
 */
@Serializable
data class TaskProposeOutputStep(
    val description: String? = null,
    val tool_name: String? = null,
    val status: TaskProposeStepStatus? = null,
    val result: String? = null
)

@Serializable
enum class TaskProposeStepStatus {
    @SerialName("pending") PENDING,
    @SerialName("approved") APPROVED,
    @SerialName("rejected") REJECTED,
    @SerialName("completed") COMPLETED
}

// =========================================================================
// Task Status
// =========================================================================

/**
 * Input for TaskStatus tool.
 */
@Serializable
data class TaskStatusInput(
    val task_id: String? = null
)

/**
 * Output for TaskStatus tool.
 */
@Serializable
data class TaskStatusOutput(
    val task_id: String? = null,
    val title: String? = null,
    val status: TaskStatusOutputStatus? = null,
    val steps: List<TaskStatusOutputStep>? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val error: String? = null
)

@Serializable
enum class TaskStatusOutputStatus {
    @SerialName("pending") PENDING,
    @SerialName("in_progress") IN_PROGRESS,
    @SerialName("completed") COMPLETED,
    @SerialName("failed") FAILED,
    @SerialName("cancelled") CANCELLED
}

@Serializable
data class TaskStatusOutputStep(
    val description: String? = null,
    val status: TaskStatusStepStatus? = null,
    val tool_name: String? = null,
    val result: String? = null,
    val error: String? = null
)

@Serializable
enum class TaskStatusStepStatus {
    @SerialName("pending") PENDING,
    @SerialName("in_progress") IN_PROGRESS,
    @SerialName("completed") COMPLETED,
    @SerialName("failed") FAILED,
    @SerialName("skipped") SKIPPED
}

// =========================================================================
// Phone Call Output (detailed)
// =========================================================================

/**
 * Detailed phone call completed output with transcript items.
 */
@Serializable
data class PhoneCallCompletedOutputDetailed(
    val status: String? = null,
    val duration_seconds: Int? = null,
    val call_id: String? = null,
    val transcript: List<PhoneCallTranscriptItem>? = null
)

/**
 * A transcript item from a phone call.
 */
@Serializable
data class PhoneCallTranscriptItem(
    val speaker: PhoneCallSpeaker? = null,
    val text: String? = null,
    val timestamp: String? = null
)

@Serializable
enum class PhoneCallSpeaker {
    @SerialName("user") USER,
    @SerialName("assistant") ASSISTANT,
    @SerialName("other") OTHER
}

// =========================================================================
// Recipe Display V0 (detailed)
// =========================================================================

/**
 * Input for RecipeDisplay V0 tool.
 */
@Serializable
data class RecipeDisplayV0Input(
    val title: String? = null,
    val description: String? = null,
    val prep_time_minutes: Int? = null,
    val cook_time_minutes: Int? = null,
    val servings: Int? = null,
    val ingredients: List<RecipeIngredient>? = null,
    val steps: List<RecipeStep>? = null,
    val notes: String? = null
)

/**
 * An ingredient in a recipe.
 */
@Serializable
data class RecipeIngredient(
    val name: String? = null,
    val quantity: Double? = null,
    val unit: RecipeIngredientUnit? = null,
    val notes: String? = null
)

@Serializable
enum class RecipeIngredientUnit {
    @SerialName("tsp") TSP,
    @SerialName("tbsp") TBSP,
    @SerialName("cup") CUP,
    @SerialName("oz") OZ,
    @SerialName("lb") LB,
    @SerialName("g") G,
    @SerialName("kg") KG,
    @SerialName("ml") ML,
    @SerialName("l") L,
    @SerialName("piece") PIECE,
    @SerialName("pinch") PINCH,
    @SerialName("to_taste") TO_TASTE,
    @SerialName("whole") WHOLE
}

/**
 * A step in a recipe.
 */
@Serializable
data class RecipeStep(
    val instruction: String? = null,
    val duration_minutes: Int? = null,
    val notes: String? = null
)

// =========================================================================
// Misc Tool Models
// =========================================================================

/**
 * Source image model for tools.
 */
@Serializable
data class SourceImage(
    val url: String? = null,
    val base64: String? = null,
    val media_type: String? = null
)

/**
 * Knowledge source for tools.
 */
@Serializable
data class KnowledgeSource(
    val type: String? = null,
    val title: String? = null,
    val content: String? = null,
    val url: String? = null,
    val document_id: String? = null
)

/**
 * Preview data model for tools.
 */
@Serializable
data class PreviewData(
    val type: String? = null,
    val title: String? = null,
    val description: String? = null,
    val image_url: String? = null,
    val url: String? = null
)

/**
 * Location preview data for map tools.
 */
@Serializable
data class LocationPreviewData(
    val name: String? = null,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val rating: Double? = null,
    val photo_url: String? = null
)

/**
 * Mobile app tool preview info.
 */
@Serializable
data class MobileAppToolPreviewInfo(
    val tool_name: String? = null,
    val display_name: String? = null,
    val icon: String? = null,
    val description: String? = null
)

/**
 * Search tool input.
 */
@Serializable
data class SearchToolInput(
    val query: String? = null,
    val max_results: Int? = null,
    val filter: String? = null
)

/**
 * Search MCP registry input.
 */
@Serializable
data class SearchMcpRegistryInput(
    val query: String? = null,
    val category: String? = null,
    val max_results: Int? = null
)

/**
 * Message compose V0 kind.
 */
@Serializable
enum class MessageComposeV0InputKind {
    @SerialName("email") EMAIL,
    @SerialName("text") TEXT,
    @SerialName("social") SOCIAL,
    @SerialName("professional") PROFESSIONAL
}

/**
 * Message compose V1 kind.
 */
@Serializable
enum class MessageComposeV1InputKind {
    @SerialName("email") EMAIL,
    @SerialName("text") TEXT,
    @SerialName("social") SOCIAL,
    @SerialName("professional") PROFESSIONAL,
    @SerialName("reply") REPLY
}

/**
 * Message compose V1 variant with detailed fields.
 */
@Serializable
data class MessageComposeV1Variant(
    val subject: String? = null,
    val body: String? = null,
    val tone: String? = null,
    val label: String? = null
)

/**
 * User location input accuracy.
 */
@Serializable
enum class UserLocationV0InputAccuracy {
    @SerialName("fine") FINE,
    @SerialName("coarse") COARSE
}

/**
 * Geocoded result in user location output.
 */
@Serializable
data class UserLocationGeocoded(
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val postal_code: String? = null
)

/**
 * User location error type.
 */
@Serializable
enum class UserLocationErrorType {
    @SerialName("permission_denied") PERMISSION_DENIED,
    @SerialName("location_unavailable") LOCATION_UNAVAILABLE,
    @SerialName("timeout") TIMEOUT,
    @SerialName("unknown") UNKNOWN
}

/**
 * Request form field type.
 */
@Serializable
enum class FormFieldType {
    @SerialName("text") TEXT,
    @SerialName("number") NUMBER,
    @SerialName("email") EMAIL,
    @SerialName("phone") PHONE,
    @SerialName("select") SELECT,
    @SerialName("multiselect") MULTISELECT,
    @SerialName("date") DATE,
    @SerialName("time") TIME,
    @SerialName("textarea") TEXTAREA,
    @SerialName("toggle") TOGGLE
}
