package com.anthropic.claude.api.project

import com.anthropic.claude.types.strings.AccountId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Lightweight account reference used to identify who created or archived a project.
 */
@Serializable
data class ProjectActorAccount(
    val uuid: AccountId? = null,
    @SerialName("full_name") val fullName: String? = null,
)
