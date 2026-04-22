package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable

/**
 * Response from the account-deletion eligibility check endpoint.
 * If [deletable] is false, [reasons] explains why deletion is blocked.
 */
@Serializable
data class AccountDeletableResponse(
    val deletable: Boolean = false,
    val reasons: List<String>? = null,
)
