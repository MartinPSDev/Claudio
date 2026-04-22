package com.anthropic.claude.api.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Permission level a member has on a project.
 * Values represent increasing levels of access from VIEW to DELETE.
 */
@Serializable
enum class ProjectPermission {
    @SerialName("UNKNOWN") UNKNOWN,
    @SerialName("VIEW") VIEW,
    @SerialName("CHAT_CREATE") CHAT_CREATE,
    @SerialName("CHAT_VIEW") CHAT_VIEW,
    @SerialName("VIEW_KNOWLEDGE") VIEW_KNOWLEDGE,
    @SerialName("EDIT_KNOWLEDGE") EDIT_KNOWLEDGE,
    @SerialName("EDIT_SETTINGS") EDIT_SETTINGS,
    @SerialName("MEMBERS_MANAGE") MEMBERS_MANAGE,
    @SerialName("OWNER_MANAGE") OWNER_MANAGE,
    @SerialName("DELETE") DELETE,
}

/**
 * Sub-classification for student-type projects.
 */
@Serializable
enum class ProjectSubtype {
    @SerialName("UNKNOWN") UNKNOWN,
    @SerialName("STUDY") STUDY,
    @SerialName("CAREER") CAREER,
    @SerialName("RESEARCH") RESEARCH,
}

/**
 * Filter option for querying the project list.
 * Each value corresponds to a server-side filter string.
 */
@Serializable
enum class ProjectFilter(val value: String) {
    @SerialName("IS_CREATOR") IS_CREATOR("IS_CREATOR"),
    @SerialName("IS_SHARED_WITH_ORG") IS_SHARED_WITH_ORG("IS_SHARED_WITH_ORG"),
    @SerialName("IS_SHARED_WITH_ME") IS_SHARED_WITH_ME("IS_SHARED_WITH_ME"),
}
