package com.anthropic.claude.app.verification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Navigation steps in the phone-number verification flow.
 */
sealed interface VerificationScreens {

    /** Step 1: user enters their phone number. */
    @Serializable
    data object EnterPhoneStep : VerificationScreens

    /** Step 2: user enters the OTP code sent to their phone. */
    @Serializable
    data class VerifyPhoneCodeStep(
        @SerialName("phone_number") val phoneNumber: String,
        @SerialName("verification_id") val verificationId: String? = null,
    ) : VerificationScreens
}
