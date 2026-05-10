package com.anthropic.claude.ui.demo.app

/**
 * Navigation destinations for the internal UI demo app.
 * Used by Anthropic engineers for testing individual UI components.
 */
enum class UiDemoAppDestination(val route: String) {
    HOME("demo_home"),
    CHAT_BUBBLE("demo_chat_bubble"),
    CODE_BLOCK("demo_code_block"),
    ARTIFACT_PREVIEW("demo_artifact_preview"),
    TOOL_RESULT("demo_tool_result"),
    MAP_DISPLAY("demo_map_display"),
    RECIPE_DISPLAY("demo_recipe_display"),
    FORM_INPUT("demo_form_input"),
    SETTINGS("demo_settings"),
    SUBSCRIPTION("demo_subscription"),
    BELL_MODE("demo_bell_mode"),
}
