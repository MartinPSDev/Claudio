package com.anthropic.claude.api.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Billing type enum representing the payment method or subscription tier of an account.
 */
@Serializable
enum class BillingType {
    @SerialName("none") NONE,
    @SerialName("prepaid") PREPAID,
    @SerialName("usage_based") USAGE_BASED,
    @SerialName("api_evaluation") API_EVALUATION,
    @SerialName("c4e_consumption_trial") C4E_CONSUMPTION_TRIAL,
    @SerialName("stripe_subscription") STRIPE_SUBSCRIPTION,
    @SerialName("stripe_self_serve_subscription") STRIPE_SELF_SERVE_SUBSCRIPTION,
    @SerialName("stripe_subscription_contracted") STRIPE_SUBSCRIPTION_CONTRACTED,
    @SerialName("stripe_subscription_enterprise_self_serve") STRIPE_SUBSCRIPTION_ENTERPRISE_SELF_SERVE,
    @SerialName("apple_subscription") APPLE_SUBSCRIPTION,
    @SerialName("google_play_subscription") GOOGLE_PLAY_SUBSCRIPTION,
    @SerialName("external_subscription_contracted") EXTERNAL_SUBSCRIPTION_CONTRACTED,
    @SerialName("aws_marketplace") AWS_MARKETPLACE,
    @SerialName("azure_ai_foundry") AZURE_AI_FOUNDRY,
}
