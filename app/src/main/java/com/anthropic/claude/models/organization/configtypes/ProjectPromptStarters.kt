package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/** Prompt starter suggestions shown in the project UI for student accounts. */
@Serializable
data class ProjectPromptStarters(
    val student: JsonElement? = null,
)
