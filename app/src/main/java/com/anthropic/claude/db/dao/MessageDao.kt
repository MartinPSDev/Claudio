package com.anthropic.claude.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.anthropic.claude.db.entity.CachedMessageEntity
import kotlinx.coroutines.flow.Flow

/** DAO for cached message operations. */
@Dao
interface MessageDao {

    @Query("SELECT * FROM cachedMessages WHERE conversation_uuid = :conversationUuid ORDER BY `index` ASC")
    fun observeByConversation(conversationUuid: String): Flow<List<CachedMessageEntity>>

    @Query("SELECT * FROM cachedMessages WHERE uuid = :uuid LIMIT 1")
    suspend fun getByUuid(uuid: String): CachedMessageEntity?

    @Upsert
    suspend fun upsertAll(messages: List<CachedMessageEntity>)

    @Upsert
    suspend fun upsert(message: CachedMessageEntity)

    @Query("DELETE FROM cachedMessages WHERE conversation_uuid = :conversationUuid")
    suspend fun deleteByConversation(conversationUuid: String)

    @Query("DELETE FROM cachedMessages WHERE uuid = :uuid")
    suspend fun deleteByUuid(uuid: String)
}
