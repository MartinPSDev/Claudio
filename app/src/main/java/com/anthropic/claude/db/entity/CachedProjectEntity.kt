package com.anthropic.claude.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cached project row — persisted locally for fast project list rendering.
 * Table: cachedProjects
 */
@Entity(tableName = "cachedProjects")
data class CachedProjectEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "name")            val name: String?,
    @ColumnInfo(name = "description")     val description: String?,
    @ColumnInfo(name = "created_at")      val createdAt: String?,
    @ColumnInfo(name = "updated_at")      val updatedAt: String?,
    @ColumnInfo(name = "organization_id") val organizationId: String?,
    @ColumnInfo(name = "is_archived")     val isArchived: Boolean = false,
    @ColumnInfo(name = "is_starred")      val isStarred: Boolean = false,
    @ColumnInfo(name = "type")            val type: String?,
)
