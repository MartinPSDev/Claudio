package com.anthropic.claude.model

import android.net.Uri

data class IncomingPayload(
    val action: String,
    val text: String? = null,
    val streamUris: List<Uri> = emptyList(),
    val deepLink: Uri? = null,
    val startChatId: String? = null,
    val startChatMode: String? = null,
)
