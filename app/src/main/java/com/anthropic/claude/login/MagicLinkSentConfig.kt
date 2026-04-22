package com.anthropic.claude.login

import com.anthropic.claude.api.login.CodeConfiguration
import com.anthropic.claude.types.strings.EmailAddress
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MagicLinkSentConfig(
    val email: EmailAddress,
    @SerialName("code_length")
    val codeLength: Int? = null,
    @SerialName("code_charset")
    val codeCharset: CodeConfiguration.CharSet? = null,
    @SerialName("code_show_input_after_delay")
    val codeShowInputAfterDelay: Int? = null,
)
