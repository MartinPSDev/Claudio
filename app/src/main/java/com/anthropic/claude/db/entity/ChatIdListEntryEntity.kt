package com.anthropic.claude.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Chat-ID list entry — lightweight row used for ordering the conversation list.
 * Table: chatIdListEntries
 */
@Entity(tableName = "chatIdListEntries")
data class ChatIdListEntryEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "position")        val position: Int,
    @ColumnInfo(name = "organization_id") val organizationId: String?,
    @ColumnInfo(name = "project_uuid")    val projectUuid: String?,
    @ColumnInfo(name = "updated_at")      val updatedAt: String?,
)
