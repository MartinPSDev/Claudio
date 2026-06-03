package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// =========================================================================
// HealthConnect DataTypes V0 (detailed)
// =========================================================================

/**
 * Input for HealthConnectDataTypes V0 tool.
 */
@Serializable
data class HealthConnectDataTypesV0Input(
    val filter: String? = null
)

/**
 * Output for HealthConnectDataTypes V0 tool.
 */
@Serializable
data class HealthConnectDataTypesV0Output(
    val data_types: List<HealthConnectDataTypeItem>? = null
)

/**
 * A data type available in Health Connect.
 */
@Serializable
data class HealthConnectDataTypeItem(
    val name: String? = null,
    val record_base_type: HealthConnectRecordBaseType? = null,
    val supported_aggregation_types: List<HealthConnectAggregationType>? = null,
    val description: String? = null
)

/**
 * Base type of a health record.
 */
@Serializable
enum class HealthConnectRecordBaseType {
    @SerialName("instant") INSTANT,
    @SerialName("interval") INTERVAL,
    @SerialName("series") SERIES
}

/**
 * Supported aggregation type.
 */
@Serializable
enum class HealthConnectAggregationType {
    @SerialName("count") COUNT,
    @SerialName("sum") SUM,
    @SerialName("average") AVERAGE,
    @SerialName("min") MIN,
    @SerialName("max") MAX,
    @SerialName("duration") DURATION
}

// =========================================================================
// HealthConnect Query V0 Input (detailed)
// =========================================================================

/**
 * Detailed query item for HealthConnect.
 */
@Serializable
data class HealthConnectQueryItem(
    val data_type: String? = null,
    val time_range: HealthConnectTimeRange? = null,
    val records: HealthConnectRecordsConfig? = null,
    val aggregation: HealthConnectAggregationConfig? = null
)

/**
 * Time range for a health query.
 */
@Serializable
data class HealthConnectTimeRange(
    val start: String? = null,
    val end: String? = null
)

/**
 * Records config for a health query.
 */
@Serializable
data class HealthConnectRecordsConfig(
    val limit: Int? = null,
    val ascending: Boolean? = null
)

/**
 * Aggregation config for a health query.
 */
@Serializable
data class HealthConnectAggregationConfig(
    val aggregation_types: List<HealthConnectAggregationTypeItem>? = null,
    val bucket_by: HealthConnectBucketBy? = null
)

@Serializable
enum class HealthConnectAggregationTypeItem {
    @SerialName("count") COUNT,
    @SerialName("sum") SUM,
    @SerialName("average") AVERAGE,
    @SerialName("min") MIN,
    @SerialName("max") MAX,
    @SerialName("duration") DURATION
}

@Serializable
enum class HealthConnectBucketBy {
    @SerialName("hour") HOUR,
    @SerialName("day") DAY,
    @SerialName("week") WEEK,
    @SerialName("month") MONTH
}

// =========================================================================
// HealthConnect Query V0 Output (detailed)
// =========================================================================

/**
 * Output for HealthConnectQuery V0 tool.
 */
@Serializable
data class HealthConnectQueryV0Output(
    val result: HealthConnectQueryV0Result? = null,
    val error: HealthConnectQueryV0Error? = null
)

@Serializable
data class HealthConnectQueryV0Result(
    val query_results: List<HealthConnectQueryResultItem>? = null
)

@Serializable
data class HealthConnectQueryResultItem(
    val data_type: String? = null,
    val records: List<HealthConnectRecordItem>? = null,
    val aggregations: List<HealthConnectAggregationResultItem>? = null,
    val error: HealthConnectQueryResultItemError? = null
)

@Serializable
data class HealthConnectRecordItem(
    val time: String? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val values: JsonElement? = null,
    val metadata: JsonElement? = null
)

@Serializable
data class HealthConnectAggregationResultItem(
    val time_range_start: String? = null,
    val time_range_end: String? = null,
    val values: List<HealthConnectAggregationValue>? = null
)

@Serializable
data class HealthConnectAggregationValue(
    val aggregation_type: HealthConnectAggregationType? = null,
    val value: Double? = null,
    val unit: String? = null
)

@Serializable
data class HealthConnectQueryResultItemError(
    val message: String? = null,
    val error_type: HealthConnectQueryErrorType? = null
)

@Serializable
data class HealthConnectQueryV0Error(
    val message: String? = null,
    val error_type: HealthConnectQueryErrorType? = null
)

@Serializable
enum class HealthConnectQueryErrorType {
    @SerialName("permission_denied") PERMISSION_DENIED,
    @SerialName("data_type_not_found") DATA_TYPE_NOT_FOUND,
    @SerialName("invalid_query") INVALID_QUERY,
    @SerialName("health_connect_unavailable") HEALTH_CONNECT_UNAVAILABLE,
    @SerialName("unknown") UNKNOWN
}
