package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A lightweight reference to a project, embedded in chat conversation objects.
 */
@Serializable
data class ProjectReference(
    val uuid: String? = null,
    val name: String? = null,
)
