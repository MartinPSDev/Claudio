package com.anthropic.claude.api.account

import kotlinx.serialization.Serializable

/**
 * Inline value class for a typed account/user identifier.
 */
@Serializable
@JvmInline
value class AccountId(val value: String)
