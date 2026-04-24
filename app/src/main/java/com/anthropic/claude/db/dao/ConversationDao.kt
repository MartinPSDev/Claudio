package com.anthropic.claude.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.anthropic.claude.db.entity.CachedConversationEntity
import com.anthropic.claude.db.entity.ChatIdListEntryEntity
import kotlinx.coroutines.flow.Flow

/** DAO for cached conversation list operations. */
@Dao
interface ConversationDao {

    @Query("SELECT * FROM cachedConversations ORDER BY updated_at DESC")
    fun observeAll(): Flow<List<CachedConversationEntity>>

    @Query("SELECT * FROM cachedConversations WHERE organization_id = :orgId ORDER BY updated_at DESC")
    fun observeByOrg(orgId: String): Flow<List<CachedConversationEntity>>

    @Query("SELECT * FROM cachedConversations WHERE uuid = :uuid LIMIT 1")
    suspend fun getByUuid(uuid: String): CachedConversationEntity?

    @Upsert
    suspend fun upsertAll(conversations: List<CachedConversationEntity>)

    @Upsert
    suspend fun upsert(conversation: CachedConversationEntity)

    @Query("DELETE FROM cachedConversations WHERE uuid = :uuid")
    suspend fun deleteByUuid(uuid: String)

    @Query("DELETE FROM cachedConversations WHERE organization_id = :orgId")
    suspend fun deleteByOrg(orgId: String)

    // ── Chat ID list entries ──────────────────────────────────────────────────

    @Query("SELECT * FROM chatIdListEntries WHERE organization_id = :orgId ORDER BY position ASC")
    fun observeIdList(orgId: String): Flow<List<ChatIdListEntryEntity>>

    @Upsert
    suspend fun upsertIdList(entries: List<ChatIdListEntryEntity>)

    @Query("DELETE FROM chatIdListEntries WHERE organization_id = :orgId")
    suspend fun deleteIdList(orgId: String)
}
