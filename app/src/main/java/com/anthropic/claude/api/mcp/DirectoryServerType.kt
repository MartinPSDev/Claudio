package com.anthropic.claude.api.mcp

import kotlinx.serialization.Serializable

@Serializable
enum class DirectoryServerType {
    REMOTE,
    LOCAL,
}
