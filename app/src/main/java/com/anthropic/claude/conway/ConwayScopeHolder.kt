package com.anthropic.claude.conway

interface ConwayScopeHolder {
    suspend fun drainPendingMessages(accountId: String, orgId: String)
}
