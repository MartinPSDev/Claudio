package com.anthropic.claude.models.organization.configtypes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Description for x-axis configuration in chart-display tool. */
@Serializable
data class ChartDisplayXAxisDescription(
    val description: String? = null,
    val title: String? = null,
    val data: String? = null,
    val format: String? = null,
    val scale: String? = null,
    val min: String? = null,
    val max: String? = null,
)

/** Description for y-axis configuration in chart-display tool. */
@Serializable
data class ChartDisplayYAxisDescription(
    val description: String? = null,
    val title: String? = null,
    val data: String? = null,
    val format: String? = null,
    val scale: String? = null,
    val min: String? = null,
    val max: String? = null,
)

/** Description for a HealthConnect query item. */
@Serializable
data class HealthConnectQueryQueriesItemDescription(
    val description: String? = null,
    val identifier: String? = null,
    val records: String? = null,
    @SerialName("time_range")  val timeRange: String? = null,
    val aggregation: String? = null,
)

/** Upsell configuration for premium feature promotion UI. */
@Serializable
data class UpsellConfig(
    val text: String? = null,
    @SerialName("dialog_title")    val dialogTitle: String? = null,
    @SerialName("feature_list")    val featureList: List<String>? = null,
    @SerialName("max_feature_list") val maxFeatureList: List<String>? = null,
)
