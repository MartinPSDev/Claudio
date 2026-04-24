package com.anthropic.claude.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.anthropic.claude.db.entity.CachedProjectEntity
import kotlinx.coroutines.flow.Flow

/** DAO for cached project operations. */
@Dao
interface ProjectDao {

    @Query("SELECT * FROM cachedProjects WHERE organization_id = :orgId AND is_archived = 0 ORDER BY updated_at DESC")
    fun observeActive(orgId: String): Flow<List<CachedProjectEntity>>

    @Query("SELECT * FROM cachedProjects WHERE organization_id = :orgId ORDER BY updated_at DESC")
    fun observeAll(orgId: String): Flow<List<CachedProjectEntity>>

    @Query("SELECT * FROM cachedProjects WHERE uuid = :uuid LIMIT 1")
    suspend fun getByUuid(uuid: String): CachedProjectEntity?

    @Upsert
    suspend fun upsertAll(projects: List<CachedProjectEntity>)

    @Upsert
    suspend fun upsert(project: CachedProjectEntity)

    @Query("DELETE FROM cachedProjects WHERE uuid = :uuid")
    suspend fun deleteByUuid(uuid: String)

    @Query("DELETE FROM cachedProjects WHERE organization_id = :orgId")
    suspend fun deleteByOrg(orgId: String)
}
