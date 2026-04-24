package com.anthropic.claude.api.experience

import kotlinx.serialization.Serializable

/** Server-driven action to open a URL in the browser. */
@Serializable
data class OpenLinkAction(val url: String? = null)
