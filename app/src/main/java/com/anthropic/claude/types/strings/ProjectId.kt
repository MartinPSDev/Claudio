package com.anthropic.claude.types.strings

import kotlinx.serialization.Serializable

/** Inline value class for a typed project identifier. */
@Serializable
@JvmInline
value class ProjectId(val value: String)
