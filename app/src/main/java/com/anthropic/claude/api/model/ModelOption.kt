package com.anthropic.claude.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A selectable model option shown in the model picker UI.
 */
@Serializable
data class ModelOption(
    val model: String,
    val name: String? = null,
    val description: String? = null,
    @SerialName("description_i18n_key") val descriptionI18nKey: String? = null,
    @SerialName("notice_text") val noticeText: String? = null,
    @SerialName("notice_text_i18n_key") val noticeTextI18nKey: String? = null,
    @SerialName("knowledge_cutoff") val knowledgeCutoff: String? = null,
    val capabilities: List<String>? = null,
    @SerialName("thinking_modes") val thinkingModes: List<String>? = null,
    val inactive: Boolean = false,
    val overflow: Boolean = false,
    @SerialName("slow_kb_warning_threshold") val slowKbWarningThreshold: Int? = null,
) {
    companion object {
        const val MISSING_MODEL_ID = "missing-model"
    }
}
