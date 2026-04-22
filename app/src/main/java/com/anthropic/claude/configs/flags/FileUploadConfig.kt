package com.anthropic.claude.configs.flags

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileUploadConfig(
    @SerialName("max_in_context_file_bytes") val maxInContextFileBytes: Int = 10 * 1024 * 1024,
    @SerialName("max_inline_paste_bytes") val maxInlinePasteBytes: Int = 500 * 1024,
) {
    companion object {
        const val FEATURE_KEY = "cai_file_upload_config"
    }
}
