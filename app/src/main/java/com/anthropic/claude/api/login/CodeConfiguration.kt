package com.anthropic.claude.api.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CodeConfiguration(
    @SerialName("code_length")
    val codeLength: Int? = null,
    @SerialName("code_charset")
    val codeCharset: CharSet? = null,
    @SerialName("code_show_input_after_delay")
    val codeShowInputAfterDelay: Int? = null,
) {
    @Serializable
    enum class CharSet {
        @SerialName("numeric") NUMERIC,
        @SerialName("alphanumeric") ALPHANUMERIC,
    }
}
