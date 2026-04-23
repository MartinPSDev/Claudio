package com.anthropic.claude.api.export

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * State of a data-export request.
 */
@Serializable
enum class ExportStatus(val value: String) {
    @SerialName("not_found")           NOT_FOUND("not_found"),
    @SerialName("ready")               READY("ready"),
    @SerialName("export_not_allowed")  EXPORT_NOT_ALLOWED("export_not_allowed"),
    @SerialName("incorrect_creator")   INCORRECT_CREATOR("incorrect_creator"),
    @SerialName("incorrect_org")       INCORRECT_ORG("incorrect_org"),
    @SerialName("export_expired")      EXPORT_EXPIRED("export_expired"),
    @SerialName("export_used")         EXPORT_USED("export_used"),
    @SerialName("unknown")             UNKNOWN("unknown"),
}

/**
 * Response from the export-status polling endpoint.
 * [signedUrl] is only populated when [status] == [ExportStatus.READY].
 */
@Serializable
data class ExportStatusResponse(
    val status: ExportStatus,
    @SerialName("signed_url") val signedUrl: String? = null,
)

/**
 * Response initiating an export — contains the export request ID.
 */
@Serializable
data class ExportDataResponse(
    @SerialName("export_id") val exportId: String? = null,
)
