package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// ─── Tool sealed hierarchy ────────────────────────────────────────────────────

/**
 * Base interface for all Claude tool definitions.
 * Each subtype maps to a specific tool capability exposed to the model.
 */
interface Tool {
    val name: String
    val type: String

    /** Web search via the internal compass/research engine. */
    object Compass : Tool {
        override val name = "Compass"
        override val type = "launch_extended_search_task"
    }

    /** Code execution / analysis tool (REPL). */
    object Analysis : Tool {
        override val name = "Analysis"
        override val type = "repl"
    }

    /** Artifacts creation and management. */
    object Artifacts : Tool {
        override val name = "Artifacts"
        override val type = "artifacts"
    }

    /** Google Drive search integration. */
    object DriveSearch : Tool {
        override val name = "DriveSearch"
        override val type = "drive_search"
    }

    /** Fallback for tool types not recognized by this client version. */
    data class Unknown(
        override val name: String,
        override val type: String,
    ) : Tool
}

// ─── Source image variants ────────────────────────────────────────────────────

/**
 * A source image referenced in a tool result — three concrete forms.
 */
@Serializable
sealed interface SourceImage {

    /** A raw URL pointing to the image. */
    @Serializable
    @SerialName("url")
    data class Url(val url: String) : SourceImage

    /** Plain text fallback (e.g. image description). */
    @Serializable
    @SerialName("text")
    data class Text(val text: String) : SourceImage

    /** A Google Doc source (URL + doc ID). */
    @Serializable
    @SerialName("google_doc")
    data class GoogleDoc(
        val url: String? = null,
        @SerialName("doc_id") val docId: String? = null,
    ) : SourceImage
}

// ─── Tool inputs ──────────────────────────────────────────────────────────────

/** Input for the web search tool. */
@Serializable
data class SearchToolInput(
    val query: String,
)

/** Input to check the status of a background task. */
@Serializable
data class TaskStatusInput(
    @SerialName("task_uuid") val taskUuid: String,
)

// ─── Preview data hierarchy ───────────────────────────────────────────────────

/** Marker interface for all tool result preview payloads shown in the UI. */
interface PreviewData

/** Calendar search results preview. */
@Serializable
data class CalendarSearchPreviewData(
    val query: String? = null,
    @SerialName("result_count") val resultCount: Int = 0,
) : PreviewData

/** Calendar create result preview. */
@Serializable
data class CalendarCreatePreviewData(
    val title: String? = null,
    @SerialName("event_id") val eventId: String? = null,
) : PreviewData

/** Calendar update result preview. */
@Serializable
data class CalendarUpdatePreviewData(
    val title: String? = null,
    @SerialName("event_id") val eventId: String? = null,
) : PreviewData

/** Calendar delete result preview. */
@Serializable
data class CalendarDeletePreviewData(
    @SerialName("event_id") val eventId: String? = null,
    val title: String? = null,
) : PreviewData

/** Location / maps preview. */
@Serializable
data class LocationPreviewData(
    val lat: Double? = null,
    val lng: Double? = null,
    val label: String? = null,
) : PreviewData

// ─── Tool invocation result ───────────────────────────────────────────────────

/** Result returned after a tool call completes. */
@Serializable
sealed interface ToolInvocationResult {

    /** Human-readable report from the tool. */
    @Serializable
    @SerialName("report")
    data class Report(
        val content: String,
        val images: List<SourceImage> = emptyList(),
    ) : ToolInvocationResult
}

// ─── MCP registry ────────────────────────────────────────────────────────────

/** Input for searching the MCP tool registry. */
@Serializable
data class SearchMcpRegistryInput(
    val query: String? = null,
    val category: String? = null,
)

// ─── Sheet destinations (UI navigation targets) ───────────────────────────────

/** Navigation destination sealed class for tool bottom sheets. */
sealed interface PhoneCallSheetDestination {
    /** Show a transcript view. */
    data class Transcript(val content: String) : PhoneCallSheetDestination
    /** Sheet is dismissed. */
    data object Closed : PhoneCallSheetDestination
}

/** Navigation destination for form-based tool sheets. */
sealed interface FormSheetDestination {
    /** Show a form input view. */
    data class FormInput(val formId: String) : FormSheetDestination
    /** Sheet is dismissed. */
    data object Closed : FormSheetDestination
}

// ─── Task preview models ──────────────────────────────────────────────────────

/** Lightweight task list output from the task tool. */
@Serializable
data class TaskListOutput(
    val tasks: List<JsonElement> = emptyList(),
)
