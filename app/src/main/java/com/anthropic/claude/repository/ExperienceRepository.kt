package com.anthropic.claude.repository

import com.anthropic.claude.api.experience.ExperienceModels
import com.anthropic.claude.api.experience.ExperienceTrackModels
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.serialization.json.Json

/**
 * Repository for server-driven experience (CTA / spotlight / banner) events.
 */
class ExperienceRepository(
    private val apiClient: AnthropicApiClient,
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getExperiences(): ApiResult<List<ExperienceModels>> =
        execute { apiClient.getExperiences() }

    suspend fun trackShown(data: ExperienceTrackModels.TrackShownData): ApiResult<Unit> =
        track("shown", data)

    suspend fun trackActioned(data: ExperienceTrackModels.TrackActionedData): ApiResult<Unit> =
        track("actioned", data)

    suspend fun trackDismissed(data: ExperienceTrackModels.TrackDismissedData): ApiResult<Unit> =
        track("dismissed", data)

    private suspend inline fun <reified D> track(action: String, data: D): ApiResult<Unit> =
        try {
            val payload = buildString {
                append("{\"action\":\"")
                append(action)
                append("\",\"data\":")
                append(json.encodeToString(data as Any))
                append("}")
            }
            val response = apiClient.trackExperience(payload)
            if (response.isSuccessful) ApiResult.Success(Unit)
            else ApiResult.Error(response.code, null)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }

    private suspend inline fun <reified T> execute(
        crossinline call: suspend () -> okhttp3.Response,
    ): ApiResult<T> = try {
        val response = call()
        val body = response.body?.string() ?: ""
        if (response.isSuccessful) ApiResult.Success(json.decodeFromString(body))
        else ApiResult.Error(response.code, body.takeIf { it.isNotBlank() })
    } catch (e: Exception) {
        ApiResult.NetworkError
    }
}
