package com.anthropic.claude.repository

import com.anthropic.claude.api.project.PaginatedProjectsResponse
import com.anthropic.claude.api.project.Project
import com.anthropic.claude.api.project.ProjectDoc
import com.anthropic.claude.api.project.ProjectCreateParams
import com.anthropic.claude.api.project.ProjectUpdateParams
import com.anthropic.claude.db.dao.ProjectDao
import com.anthropic.claude.db.entity.CachedProjectEntity
import com.anthropic.claude.networking.AnthropicApiClient
import com.anthropic.claude.networking.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

/**
 * Repository for projects.
 * Combines Room cache with AnthropicApiClient network calls.
 */
class ProjectRepository(
    private val apiClient: AnthropicApiClient,
    private val projectDao: ProjectDao,
) {
    private val json = Json { ignoreUnknownKeys = true }

    // ── Observe (Room — reactive) ─────────────────────────────────────────────

    fun observeActiveProjects(orgId: String): Flow<List<CachedProjectEntity>> =
        projectDao.observeActive(orgId)

    fun observeAllProjects(orgId: String): Flow<List<CachedProjectEntity>> =
        projectDao.observeAll(orgId)

    // ── Fetch & cache ─────────────────────────────────────────────────────────

    suspend fun refreshProjects(orgId: String) {
        try {
            val response = apiClient.getProjects(orgId)
            if (response.isSuccessful) {
                val body = response.body?.string() ?: return
                val projects = try {
                    json.decodeFromString<PaginatedProjectsResponse>(body).projects
                } catch (_: Exception) {
                    json.decodeFromString<List<Project>>(body)
                }
                val entities = projects.map { it.toCachedEntity(orgId) }
                projectDao.upsertAll(entities)
            }
        } catch (_: Exception) { }
    }

    suspend fun fetchProject(projectId: String): CachedProjectEntity? =
        projectDao.getByUuid(projectId)

    // ── Mutations ─────────────────────────────────────────────────────────────

    suspend fun createProject(orgId: String, params: ProjectCreateParams): ApiResult<Project> =
        execute { apiClient.createProject(orgId, params) }

    suspend fun updateProject(orgId: String, projectId: String, params: ProjectUpdateParams): ApiResult<Project> =
        execute { apiClient.updateProject(orgId, projectId, params) }

    suspend fun deleteProject(orgId: String, projectId: String): ApiResult<Unit> {
        projectDao.deleteByUuid(projectId)
        return try {
            val response = apiClient.deleteProject(orgId, projectId)
            if (response.isSuccessful) ApiResult.Success(Unit)
            else ApiResult.Error(response.code, null)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    // ── Docs ─────────────────────────────────────────────────────────────────

    suspend fun getProjectDocs(orgId: String, projectId: String): ApiResult<List<ProjectDoc>> =
        execute { apiClient.getProjectDocs(orgId, projectId) }

    suspend fun deleteProjectDoc(orgId: String, projectId: String, docId: String): ApiResult<Unit> =
        try {
            val response = apiClient.deleteProjectDoc(orgId, projectId, docId)
            if (response.isSuccessful) ApiResult.Success(Unit)
            else ApiResult.Error(response.code, null)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }

    // ── Helpers ───────────────────────────────────────────────────────────────

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

    private fun Project.toCachedEntity(orgId: String) = CachedProjectEntity(
        uuid = uuid,
        orgId = orgId,
        name = name,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isArchived = isArchived,
    )
}
