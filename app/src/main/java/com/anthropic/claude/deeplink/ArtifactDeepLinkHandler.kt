package com.anthropic.claude.deeplink

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Handles deep links for artifacts and shared conversations.
 *
 * When the `mobile_artifact_deep_link_kill_switch` feature flag is enabled,
 * the link is opened in the system browser instead of being handled in-app.
 * Otherwise, the in-app navigation callback is used.
 */
class ArtifactDeepLinkHandler(
    private val context: Context,
    private val featureFlagProvider: FeatureFlagProvider,
) {

    /**
     * Processes an artifact deep link.
     *
     * @param deepLink The deep link data containing the URL and artifact ID.
     * @param onNavigateInApp Callback invoked when the link should be handled in-app.
     *                        Receives the artifact ID and whether it's a shared artifact.
     */
    fun handle(
        deepLink: ArtifactDeepLink,
        onNavigateInApp: (artifactId: String, isShared: Boolean) -> Unit,
    ) {
        val isKillSwitchActive = featureFlagProvider
            .getBoolean("mobile_artifact_deep_link_kill_switch")

        if (isKillSwitchActive) {
            openInBrowser(deepLink.browserUrl)
        } else {
            onNavigateInApp(deepLink.artifactId, deepLink.isShared)
        }
    }

    private fun openInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            setPackage(getDefaultBrowserPackage())
        }
        context.startActivity(intent)
    }

    private fun getDefaultBrowserPackage(): String? {
        return context.packageManager
            .resolveActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://")),
                0,
            )?.activityInfo?.packageName
    }
}

/**
 * Data class representing a deep link to a specific artifact.
 */
data class ArtifactDeepLink(
    /** Full URL for opening in the browser. */
    val browserUrl: String,
    /** The artifact UUID for in-app navigation. */
    val artifactId: String,
    /** Whether this is a publicly shared artifact. */
    val isShared: Boolean = false,
)

/**
 * Abstraction over feature flag evaluation (GrowthBook).
 */
interface FeatureFlagProvider {
    fun getBoolean(key: String): Boolean
}
