package com.anthropic.claude.core.config

import android.content.Context
import android.content.pm.PackageManager

/**
 * Detects the Android device form factor by querying PackageManager system features.
 * Used by Sentry and analytics to tag events with the correct device type.
 */
object DeviceTypeDetector {

    /**
     * Returns a short string identifying the device form factor.
     *
     * | System feature | Return value |
     * |---|---|
     * | `android.hardware.type.television` | `"tv"` |
     * | `android.hardware.type.watch` | `"watch"` |
     * | `android.hardware.type.automotive` | `"auto"` |
     * | `android.hardware.type.embedded` | `"embedded"` |
     * | (none / phone/tablet) | `""` |
     */
    fun detect(context: Context): String {
        val pm = context.packageManager
        return when {
            pm.hasSystemFeature(PackageManager.FEATURE_TELEVISION) ||
            pm.hasSystemFeature("android.hardware.type.television") -> "tv"

            pm.hasSystemFeature(PackageManager.FEATURE_WATCH)       -> "watch"

            pm.hasSystemFeature("android.hardware.type.automotive")  -> "auto"

            pm.hasSystemFeature("android.hardware.type.embedded")    -> "embedded"

            else -> ""
        }
    }
}
