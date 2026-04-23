package com.anthropic.claude.api.verification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Request body to send an SMS verification code to a phone number. */
@Serializable
data class SendPhoneVerificationCodeRequest(
    @SerialName("phone_number") val phoneNumber: String,
)

/** Response indicating whether the SMS was dispatched. */
@Serializable
data class SendPhoneVerificationCodeResponse(
    val sent: Boolean,
)

/** Request body to verify an OTP code received via SMS. */
@Serializable
data class VerifyPhoneVerificationCodeRequest(
    @SerialName("phone_number") val phoneNumber: String,
    val code: String,
)
