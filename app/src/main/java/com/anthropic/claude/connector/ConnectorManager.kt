package com.anthropic.claude.connector

import android.util.Log
import com.anthropic.claude.networking.AnthropicApiClient
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Manages third-party service connectors (integrations) for Claude.
 *
 * Connectors include:
 *   - Google Drive
 *   - Google Calendar
 *   - Notion
 *   - GitHub
 *   - Zapier
 *
 * Each connector can be authorized, listed, and disconnected.
 * Connected services appear as available tools in conversations.
 */
class ConnectorManager(
    private val apiClient: AnthropicApiClient,
) {
    companion object {
        private const val TAG = "ConnectorManager"
    }

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Lists all available connectors and their authorization status.
     */
    suspend fun listConnectors(orgId: String): List<ConnectorInfo> {
        return try {
            val response = apiClient.listConnectors(orgId)
            if (response.isSuccessful) {
                val body = response.body?.string() ?: "[]"
                json.decodeFromString<List<ConnectorInfo>>(body)
            } else {
                Log.e(TAG, "Failed to list connectors: ${response.code}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error listing connectors", e)
            emptyList()
        }
    }

    /**
     * Initiates OAuth authorization for a connector.
     *
     * @return The OAuth URL to open in a browser, or null on failure.
     */
    suspend fun authorize(orgId: String, connectorId: String): String? {
        return try {
            val response = apiClient.authorizeConnector(orgId, connectorId)
            if (response.isSuccessful) {
                val body = response.body?.string() ?: return null
                val result = json.decodeFromString<AuthorizeResponse>(body)
                result.authUrl
            } else {
                Log.e(TAG, "Failed to authorize connector: ${response.code}")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error authorizing connector", e)
            null
        }
    }

    /**
     * Disconnects a previously authorized connector.
     */
    suspend fun disconnect(orgId: String, connectorId: String): Boolean {
        return try {
            val response = apiClient.disconnectConnector(orgId, connectorId)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e(TAG, "Error disconnecting connector", e)
            false
        }
    }
}

// ── Models ───────────────────────────────────────────────────────────────

@Serializable
data class ConnectorInfo(
    val id: String,
    val name: String,
    val type: String,
    val description: String? = null,
    val iconUrl: String? = null,
    val isAuthorized: Boolean = false,
    val scopes: List<String> = emptyList(),
    val lastSyncedAt: String? = null,
)

@Serializable
data class AuthorizeResponse(
    val authUrl: String,
)
