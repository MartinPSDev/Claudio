package com.anthropic.claude.app.onboarding.v2

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sealed interface representing a page in the v2 onboarding flow.
 * Each subtype corresponds to a discrete step shown to new users.
 */
sealed interface OnboardingPage {
    val id: String

    @Serializable
    @SerialName("intro")
    data object Intro : OnboardingPage {
        override val id = "intro"
    }

    @Serializable
    @SerialName("name")
    data object Name : OnboardingPage {
        override val id = "name"
    }

    @Serializable
    @SerialName("age")
    data object Age : OnboardingPage {
        override val id = "age"
    }

    @Serializable
    @SerialName("phone")
    data object Phone : OnboardingPage {
        override val id = "phone"
    }

    @Serializable
    @SerialName("permission")
    data object Permission : OnboardingPage {
        override val id = "permission"
    }

    @Serializable
    @SerialName("grove")
    data object Grove : OnboardingPage {
        override val id = "grove"
    }

    @Serializable
    @SerialName("subscription")
    data object Subscription : OnboardingPage {
        override val id = "subscription"
    }
}
