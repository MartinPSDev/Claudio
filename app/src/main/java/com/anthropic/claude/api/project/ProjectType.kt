package com.anthropic.claude.api.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Classification of a Claude project type.
 * HAYSTACK corresponds to the "Docs" / knowledge-base project variant.
 * STUDENT is used for educational accounts.
 */
@Serializable
enum class ProjectType {
    @SerialName("UNKNOWN") UNKNOWN,
    @SerialName("STUDENT") STUDENT,
    @SerialName("HAYSTACK") HAYSTACK,
}
