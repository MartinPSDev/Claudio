package com.anthropic.claude.login

import com.anthropic.claude.api.login.CodeConfiguration
import com.anthropic.claude.types.strings.EmailAddress
import kotlinx.serialization.Serializable

/**
 * Configuration delivered to the "magic link sent" screen after the user
 * requests an email login. Controls OTP display behaviour.
 *
 * @param email The address the magic link / OTP was sent to.
 * @param codeLength Expected length of the one-time code (null = server default).
 * @param codeCharset Character set of the OTP (numeric, alphanumeric, etc.).
 * @param codeShowInputAfterDelay Seconds to wait before showing the code input field
 *   (null = show immediately).
 */
@Serializable
data class MagicLinkSentConfig(
    val email: EmailAddress,
    val codeLength: Int? = null,
    val codeCharset: CodeConfiguration.CharSet? = null,
    val codeShowInputAfterDelay: Int? = null,
)
