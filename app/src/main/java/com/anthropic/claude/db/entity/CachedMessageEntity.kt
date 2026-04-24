package com.anthropic.claude.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Cached message row — persisted locally for offline chat rendering.
 * Table: cachedMessages
 */
@Entity(
    tableName = "cachedMessages",
    foreignKeys = [
        ForeignKey(
            entity = CachedConversationEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["conversation_uuid"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("conversation_uuid")],
)
data class CachedMessageEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "conversation_uuid") val conversationUuid: String,
    @ColumnInfo(name = "sender")            val sender: String?,
    @ColumnInfo(name = "content_json")      val contentJson: String?,
    @ColumnInfo(name = "created_at")        val createdAt: String?,
    @ColumnInfo(name = "model")             val model: String?,
    @ColumnInfo(name = "index")             val index: Int?,
    @ColumnInfo(name = "stop_reason")       val stopReason: String?,
)
