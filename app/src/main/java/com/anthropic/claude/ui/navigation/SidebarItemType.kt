package com.anthropic.claude.ui.navigation

/**
 * Items displayed in the navigation drawer sidebar.
 *
 * The order reflects the visual layout in the drawer:
 * header → new chat → sections → starred → recents → projects → bottom spacer.
 */
enum class SidebarItemType {
    HEADER,
    NEW_CHAT,
    CHATS,
    PROJECTS,
    CODE_REMOTE,
    AGENT_CHAT,
    CONWAY,
    ORBIT,
    TASKS,
    ARTIFACTS,
    EDUCATION_UPSELL,
    PROJECTS_DIVIDER,
    STARRED_SECTION_DIVIDER,
    STARRED_SECTION_HEADER,
    RECENTS_SECTION_DIVIDER,
    RECENTS_SECTION_HEADER,
    PROJECTS_HEADER,
    PROJECTS_EMPTY_STATE,
    PROJECTS_UPSELL,
    ALL_PROJECTS,
    SEARCH_EMPTY_STATE,
    BOTTOM_SPACER,
}
