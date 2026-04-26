package com.anthropic.claude.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService

/**
 * Registers all notification channels required by the app.
 *
 * Channels are created at app startup via [createAll]. Conditional channels
 * (bogosort, dispatch, completion, marketing) are only registered when the
 * matching feature flag is enabled.
 */
object NotificationChannels {

    // ── Channel IDs ───────────────────────────────────────────────────────────

    /** Bell Mode / Voice Mode channel. */
    const val VOICE_MODE      = "voice_mode_notification_channel"

    /** TTS Playback channel. */
    const val TTS_PLAYBACK    = "tts_playback_notification_channel"

    /** Compass — Claude-generated insights. IMPORTANCE_HIGH. */
    const val COMPASS         = "compass"

    /** Assist — Background assistant actions. IMPORTANCE_HIGH. */
    const val ASSIST          = "assist"

    /** Orbit Insight. IMPORTANCE_HIGH. */
    const val ORBIT_INSIGHT   = "orbit_insight"

    /** Bogosort (feature-flagged). IMPORTANCE_HIGH. */
    const val BOGOSORT        = "bogosort"

    /** Dispatch (feature-flagged). IMPORTANCE_HIGH. */
    const val DISPATCH        = "dispatch"

    /** Completion notifications (feature-flagged). IMPORTANCE_HIGH. */
    const val COMPLETION      = "completion"

    /** Marketing notifications (feature-flagged). IMPORTANCE_DEFAULT. */
    const val MARKETING       = "marketing"

    // ── Registration ─────────────────────────────────────────────────────────

    /**
     * Creates all notification channels with the correct importance levels.
     *
     * @param context Application context.
     * @param flags   Feature flags that control conditional channels.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun createAll(context: Context, flags: ChannelFlags) {
        val manager = context.getSystemService<NotificationManager>() ?: return

        val channels = buildList {
            // Always-on channels
            add(buildChannel(VOICE_MODE,   context, R.string.channel_voice_mode_name,   NotificationManager.IMPORTANCE_LOW,     showBadge = false))
            add(buildChannel(TTS_PLAYBACK, context, R.string.channel_tts_playback_name, NotificationManager.IMPORTANCE_DEFAULT,  showBadge = false))
            add(buildChannel(COMPASS,      context, R.string.channel_compass_name,       NotificationManager.IMPORTANCE_HIGH,    showBadge = true,  descriptionRes = R.string.channel_compass_desc))
            add(buildChannel(ASSIST,       context, R.string.channel_assist_name,        NotificationManager.IMPORTANCE_HIGH,    showBadge = true,  descriptionRes = R.string.channel_assist_desc))
            add(buildChannel(ORBIT_INSIGHT,context, R.string.channel_orbit_insight_name, NotificationManager.IMPORTANCE_HIGH,   showBadge = true,  descriptionRes = R.string.channel_orbit_insight_desc))

            // Feature-flagged channels
            if (flags.isBogosortEnabled) {
                add(buildChannel(BOGOSORT, context, R.string.channel_bogosort_name, NotificationManager.IMPORTANCE_HIGH, showBadge = true, descriptionRes = R.string.channel_bogosort_desc))
            }
            if (flags.isDispatchEnabled) {
                add(buildChannel(DISPATCH, context, R.string.channel_dispatch_name, NotificationManager.IMPORTANCE_HIGH, showBadge = true, descriptionRes = R.string.channel_dispatch_desc))
            }
            if (flags.isCompletionEnabled) {
                add(buildChannel(COMPLETION, context, R.string.channel_completion_name, NotificationManager.IMPORTANCE_HIGH, showBadge = true, descriptionRes = R.string.channel_completion_desc))
            }
            if (flags.isMarketingEnabled) {
                add(buildChannel(MARKETING, context, R.string.channel_marketing_name, NotificationManager.IMPORTANCE_DEFAULT, showBadge = true, descriptionRes = R.string.channel_marketing_desc))
            }
        }

        manager.createNotificationChannels(channels)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildChannel(
        id: String,
        context: Context,
        nameRes: Int,
        importance: Int,
        showBadge: Boolean,
        descriptionRes: Int? = null,
    ) = NotificationChannel(id, context.getString(nameRes), importance).apply {
        setShowBadge(showBadge)
        if (descriptionRes != null) description = context.getString(descriptionRes)
    }

    /**
     * Feature flags controlling conditional notification channel registration.
     */
    data class ChannelFlags(
        val isBogosortEnabled: Boolean,
        val isDispatchEnabled: Boolean,
        val isCompletionEnabled: Boolean,
        val isMarketingEnabled: Boolean,
    )
}
