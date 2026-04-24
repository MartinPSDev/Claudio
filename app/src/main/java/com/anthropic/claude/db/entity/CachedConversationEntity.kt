package com.anthropic.claude.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cached conversation row — persisted locally for offline access and fast list rendering.
 * Table: cachedConversations
 */
@Entity(tableName = "cachedConversations")
data class CachedConversationEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "title")           val title: String?,
    @ColumnInfo(name = "created_at")      val createdAt: String?,
    @ColumnInfo(name = "updated_at")      val updatedAt: String?,
    @ColumnInfo(name = "project_uuid")    val projectUuid: String?,
    @ColumnInfo(name = "organization_id") val organizationId: String?,
    @ColumnInfo(name = "is_starred")      val isStarred: Boolean = false,
    @ColumnInfo(name = "is_pinned")       val isPinned: Boolean = false,
    @ColumnInfo(name = "model")           val model: String?,
    @ColumnInfo(name = "summary")         val summary: String?,
)
