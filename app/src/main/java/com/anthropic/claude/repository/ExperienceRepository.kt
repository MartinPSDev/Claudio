package com.anthropic.claude.repository

import com.anthropic.claude.api.experience.ExperienceModels
import com.anthropic.claude.api.experience.ExperienceTrackModels
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult

/**
 * Repository for server-driven experience (CTA / spotlight / banner) events.
 */
class ExperienceRepository(
    private val apiClient: AnthropicApiClient,
) {
    suspend fun getExperiences(): ApiResult<List<ExperienceModels>> {
        // TODO: apiClient.getExperiences()
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun trackShown(data: ExperienceTrackModels.TrackShownData): ApiResult<Unit> {
        // TODO: apiClient.trackExperience(ExperienceActionRequest(action="shown", data=...))
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun trackActioned(data: ExperienceTrackModels.TrackActionedData): ApiResult<Unit> {
        // TODO: apiClient.trackExperience(ExperienceActionRequest(action="actioned", data=...))
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun trackDismissed(data: ExperienceTrackModels.TrackDismissedData): ApiResult<Unit> {
        // TODO: apiClient.trackExperience(ExperienceActionRequest(action="dismissed", data=...))
        return ApiResult.Error(501, "Not implemented")
    }
}
