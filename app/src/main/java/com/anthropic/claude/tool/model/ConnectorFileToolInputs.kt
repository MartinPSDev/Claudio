package com.anthropic.claude.tool.model

import kotlinx.serialization.Serializable

/**
 * Input for the suggest-connectors tool — provides a list of connector UUIDs to evaluate.
 */
@Serializable
data class SuggestConnectorsInput(
    val uuids: List<String>? = null,
)

/**
 * Input for the present-files tool — provides file paths to present to the user.
 */
@Serializable
data class PresentFilesInput(
    val filepaths: List<String>? = null,
)
