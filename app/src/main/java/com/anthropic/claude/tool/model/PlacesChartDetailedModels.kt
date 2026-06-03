package com.anthropic.claude.tool.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// =========================================================================
// PlacesMapDisplay V0 Input (detailed)
// =========================================================================

/**
 * Detailed input for the PlacesMapDisplay V0 tool.
 * Supports both simple location lists and multi-day itineraries.
 */
@Serializable
data class PlacesMapDisplayV0Input(
    val locations: List<PlacesLocationItem>? = null,
    val days: List<PlacesDaysItem>? = null,
    val mode: PlacesMapDisplayMode? = null,
    val travel_mode: PlacesTravelMode? = null,
    val title: String? = null
)

/**
 * A single location in a places map display.
 */
@Serializable
data class PlacesLocationItem(
    val name: String? = null,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val description: String? = null,
    val category: String? = null
)

/**
 * A day in a multi-day itinerary.
 *
 * Fields: day_number (Int), locations, narrative, title
 */
@Serializable
data class PlacesDaysItem(
    val day_number: Int,
    val locations: List<PlacesDaysItemLocation>,
    val narrative: String? = null,
    val title: String? = null
)

/**
 * A location within a day's itinerary.
 */
@Serializable
data class PlacesDaysItemLocation(
    val name: String? = null,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val description: String? = null,
    val duration_minutes: Int? = null,
    val category: String? = null,
    val order: Int? = null
)

/**
 * Display mode for places map.
 */
@Serializable
enum class PlacesMapDisplayMode {
    @SerialName("markers") MARKERS,
    @SerialName("route") ROUTE,
    @SerialName("itinerary") ITINERARY
}

/**
 * Travel mode for route display.
 */
@Serializable
enum class PlacesTravelMode {
    @SerialName("driving") DRIVING,
    @SerialName("walking") WALKING,
    @SerialName("transit") TRANSIT,
    @SerialName("bicycling") BICYCLING
}

// =========================================================================
// PlacesMapDisplay V0 Output (detailed)
// =========================================================================

/**
 * Detailed enriched place from Google Places API.
 */
@Serializable
data class PlacesEnrichedPlace(
    val name: String? = null,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val rating: Double? = null,
    val user_ratings_total: Int? = null,
    val place_id: String? = null,
    val price_level: Int? = null,
    val website: String? = null,
    val phone_number: String? = null,
    val opening_hours: String? = null,
    val photos: List<PlacesEnrichedPlacePhoto>? = null
)

/**
 * Photo in an enriched place.
 */
@Serializable
data class PlacesEnrichedPlacePhoto(
    val photo_reference: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val attributions: List<PlacesPhotoAttribution>? = null
)

/**
 * Attribution for a places photo.
 */
@Serializable
data class PlacesPhotoAttribution(
    val html_attribution: String? = null
)

// =========================================================================
// Chart Display V0 Input (detailed)
// =========================================================================

/**
 * Detailed input for the ChartDisplay V0 tool.
 */
@Serializable
data class ChartDisplayV0Input(
    val title: String? = null,
    val series: List<ChartSeriesItem>? = null,
    val x_axis: ChartXAxis? = null,
    val y_axis: ChartYAxis? = null,
    val style: ChartStyle? = null
)

/**
 * A data series in a chart.
 */
@Serializable
data class ChartSeriesItem(
    val name: String? = null,
    val points: List<ChartPointItem>? = null,
    val type: String? = null,
    val color: String? = null
)

/**
 * A data point in a series.
 */
@Serializable
data class ChartPointItem(
    val x: Double? = null,
    val y: Double? = null,
    val label: String? = null
)

/**
 * X-axis configuration.
 */
@Serializable
data class ChartXAxis(
    val label: String? = null,
    val scale: ChartAxisScale? = null,
    val min: Double? = null,
    val max: Double? = null
)

/**
 * Y-axis configuration.
 */
@Serializable
data class ChartYAxis(
    val label: String? = null,
    val scale: ChartAxisScale? = null,
    val min: Double? = null,
    val max: Double? = null
)

/**
 * Axis scale type.
 */
@Serializable
enum class ChartAxisScale {
    @SerialName("linear") LINEAR,
    @SerialName("log") LOG,
    @SerialName("time") TIME,
    @SerialName("category") CATEGORY
}

/**
 * Chart visual style.
 */
@Serializable
enum class ChartStyle {
    @SerialName("line") LINE,
    @SerialName("bar") BAR,
    @SerialName("scatter") SCATTER,
    @SerialName("area") AREA,
    @SerialName("pie") PIE
}

/**
 * Chart display output status.
 */
@Serializable
enum class ChartDisplayV0OutputStatus {
    @SerialName("success") SUCCESS,
    @SerialName("error") ERROR
}
