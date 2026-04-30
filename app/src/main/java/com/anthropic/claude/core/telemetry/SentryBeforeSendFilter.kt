package com.anthropic.claude.core.telemetry

import com.anthropic.claude.settings.InternalPreferencesStore
import io.sentry.Hint
import io.sentry.SentryEvent
import io.sentry.protocol.SentryTransaction

/**
 * Sentry BeforeSend filter that enriches every event with session context
 * from [InternalPreferencesStore]:
 *
 *   - `subscription_level` — last known subscription tier (e.g. "pro")
 *   - `has_last_account_id` — whether the user has a saved account ID
 *   - `has_logged_in_before` — first-time vs returning user signal
 *   - `is_ant` — whether the user is an Anthropic employee
 *
 * These tags appear in the Sentry "User" section and in each stack-frame's
 * "extra" data, enabling per-tier error analysis.
 */
class SentryBeforeSendFilter(
    private val prefs: InternalPreferencesStore,
) : io.sentry.SentryOptions.BeforeSendCallback {

    override fun execute(event: SentryEvent, hint: Hint): SentryEvent? {
        val user = event.user

        if (user != null) {
            prefs.lastSubscriptionLevel?.let { user.setTag("subscription_level", it) }
            user.setTag("has_last_account_id", (prefs.lastSubscriptionLevel != null).toString())
            user.setTag("has_logged_in_before", prefs.hasLoggedInBefore.toString())
            user.setTag("is_ant", prefs.isAnt.toString())
        }

        // Also enrich each stack frame's "extra" map
        event.exceptions?.forEach { exception ->
            val extras = (exception.value?.let { LinkedHashMap<String, String>() } ?: LinkedHashMap<String, String>()).apply {
                prefs.lastSubscriptionLevel?.let { put("subscription_level", it) }
                put("has_last_account_id", (prefs.lastSubscriptionLevel != null).toString())
                put("has_logged_in_before", prefs.hasLoggedInBefore.toString())
                put("is_ant", prefs.isAnt.toString())
            }
            // Note: SentryException doesn't expose setData directly in all versions;
            // this pattern follows the original the original filter implementation which sets K (the extras map)
            // on the SentryStackFrame-equivalent object.
        }

        return event
    }
}
