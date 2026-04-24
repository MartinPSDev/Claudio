package com.anthropic.claude.repository

import com.anthropic.claude.api.project.Project
import com.anthropic.claude.api.project.ProjectDoc
import com.anthropic.claude.api.project.ProjectCreateParams
import com.anthropic.claude.api.project.ProjectUpdateParams
import com.anthropic.claude.db.dao.ProjectDao
import com.anthropic.claude.db.entity.CachedProjectEntity
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository for projects.
 * Combines Room cache with AnthropicApiClient network calls.
 */
class ProjectRepository(
    private val apiClient: AnthropicApiClient,
    private val projectDao: ProjectDao,
) {
    // ── Observe (Room — reactive) ─────────────────────────────────────────────

    fun observeActiveProjects(orgId: String): Flow<List<CachedProjectEntity>> =
        projectDao.observeActive(orgId)

    fun observeAllProjects(orgId: String): Flow<List<CachedProjectEntity>> =
        projectDao.observeAll(orgId)

    // ── Fetch & cache ─────────────────────────────────────────────────────────

    suspend fun refreshProjects(orgId: String) {
        // TODO: apiClient.getProjects(orgId) → map to entities → projectDao.upsertAll(entities)
    }

    suspend fun fetchProject(projectId: String): CachedProjectEntity? =
        projectDao.getByUuid(projectId)

    // ── Mutations ─────────────────────────────────────────────────────────────

    suspend fun createProject(params: ProjectCreateParams): ApiResult<Project> {
        // TODO: apiClient.createProject(params) → upsert to Room
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun updateProject(projectId: String, params: ProjectUpdateParams): ApiResult<Project> {
        // TODO: apiClient.updateProject(projectId, params) → upsert to Room
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun deleteProject(projectId: String): ApiResult<Unit> {
        projectDao.deleteByUuid(projectId)
        // TODO: apiClient.deleteProject(projectId)
        return ApiResult.Error(501, "Not implemented")
    }

    // ── Docs ─────────────────────────────────────────────────────────────────

    suspend fun getProjectDocs(projectId: String): ApiResult<List<ProjectDoc>> {
        // TODO: apiClient.getProjectDocs(projectId)
        return ApiResult.Error(501, "Not implemented")
    }

    suspend fun deleteProjectDoc(projectId: String, docId: String): ApiResult<Unit> {
        // TODO: apiClient.deleteProjectDoc(projectId, docId)
        return ApiResult.Error(501, "Not implemented")
    }
}
