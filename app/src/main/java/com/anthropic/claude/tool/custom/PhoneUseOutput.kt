package com.anthropic.claude.tool.custom

import kotlinx.serialization.Serializable

/** Output of the phone-use custom tool. */
@Serializable
data class PhoneUseOutput(val text: String? = null)
