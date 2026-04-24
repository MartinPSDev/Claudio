package com.anthropic.claude.types.strings

import kotlinx.serialization.Serializable

/** Inline value class wrapping a typed organization identifier. */
@Serializable
@JvmInline
value class OrganizationId(val value: String)

/** Inline value class wrapping a typed app session identifier. */
@Serializable
@JvmInline
value class AppSessionId(val value: String)
