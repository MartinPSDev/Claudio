package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Configuration for copying project templates for student accounts.
 */
@Serializable
data class ProjectTemplatesCopyConfig(
    val student: JsonElement? = null,
)
