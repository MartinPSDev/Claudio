package com.anthropic.claude.tool.model

import kotlinx.serialization.Serializable

/**
 * Sealed interface for known tool types in the application.
 *
 * Subtypes: Analysis, WebSearch, DriveSearch, Compass, Artifacts, Unknown
 */
@Serializable
sealed interface ToolModel {
    val name: String

    @Serializable
    data class Analysis(override val name: String = "analysis") : ToolModel

    @Serializable
    data class WebSearch(override val name: String = "web_search") : ToolModel

    @Serializable
    data class DriveSearch(override val name: String = "drive_search") : ToolModel

    @Serializable
    data class Compass(override val name: String = "compass") : ToolModel

    @Serializable
    data class Artifacts(override val name: String = "artifacts") : ToolModel

    @Serializable
    data class Unknown(override val name: String) : ToolModel
}
