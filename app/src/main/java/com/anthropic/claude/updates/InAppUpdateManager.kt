package com.anthropic.claude.updates

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import java.util.concurrent.TimeUnit

/**
 * Manages the Play Core in-app update flow (flexible and immediate).
 *
 * Flow:
 * 1. Check if update is available via [checkForUpdate].
 * 2. If flexible update is appropriate (staleness >= config threshold,
 *    cooldown elapsed since last dismiss), start flexible flow.
 * 3. If immediate update is required (forced by server), start immediate flow.
 * 4. Listen for install state changes and call [completeUpdate] when downloaded.
 */
class InAppUpdateManager(
    private val appUpdateManager: AppUpdateManager,
    private val prefs: SharedPreferences,
) {
    companion object {
        private const val TAG = "InAppUpdateManager"
        private const val KEY_DISMISSED_AT = "flexible_update_dismissed_at"
    }

    private var installStateListener: InstallStateUpdatedListener? = null

    /**
     * Checks if an update is available and whether it should be shown.
     *
     * @param config The flexible update configuration from feature flags.
     * @return [UpdateAction] describing what to do.
     */
    fun checkForUpdate(
        config: FlexibleUpdateConfig,
        onResult: (UpdateAction) -> Unit,
    ) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val action = evaluateUpdate(info, config)
            onResult(action)
        }.addOnFailureListener { e ->
            Log.e(TAG, "Failed to check for updates", e)
            onResult(UpdateAction.None)
        }
    }

    /**
     * Starts a flexible update flow.
     */
    fun startFlexibleUpdate(activity: Activity, updateInfo: AppUpdateInfo) {
        installStateListener = InstallStateUpdatedListener { state ->
            when (state.installStatus()) {
                InstallStatus.DOWNLOADED -> {
                    Log.i(TAG, "Update downloaded, ready to install")
                }
                InstallStatus.FAILED -> {
                    Log.e(TAG, "Flexible update failed")
                }
                else -> { /* progress */ }
            }
        }
        installStateListener?.let { appUpdateManager.registerListener(it) }

        appUpdateManager.startUpdateFlow(
            updateInfo,
            activity,
            AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build(),
        )
    }

    /**
     * Starts an immediate (blocking) update flow.
     */
    fun startImmediateUpdate(activity: Activity, updateInfo: AppUpdateInfo) {
        appUpdateManager.startUpdateFlow(
            updateInfo,
            activity,
            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
        )
    }

    /**
     * Completes a flexible update that has been downloaded.
     * This will restart the app.
     */
    fun completeUpdate() {
        Log.i(TAG, "completeUpdate(flexible)")
        appUpdateManager.completeUpdate()
    }

    /**
     * Records that the user dismissed the flexible update prompt.
     */
    fun recordDismiss() {
        prefs.edit().putLong(KEY_DISMISSED_AT, System.currentTimeMillis()).apply()
    }

    /**
     * Cleans up the install state listener.
     */
    fun unregisterListener() {
        installStateListener?.let { appUpdateManager.unregisterListener(it) }
        installStateListener = null
    }

    // ── Private ──────────────────────────────────────────────────────────────

    private fun evaluateUpdate(
        info: AppUpdateInfo,
        config: FlexibleUpdateConfig,
    ): UpdateAction {
        if (info.updateAvailability() != UpdateAvailability.UPDATE_AVAILABLE) {
            return UpdateAction.None
        }

        // Immediate update takes priority if available
        if (info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
            return UpdateAction.Immediate(info)
        }

        // Flexible update check
        if (!config.enabled) return UpdateAction.None
        if (!info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) return UpdateAction.None

        val stalenessDays = info.clientVersionStalenessDays() ?: 0
        if (stalenessDays < config.minStalenessDays) return UpdateAction.None

        // Cooldown check
        val dismissedAt = prefs.getLong(KEY_DISMISSED_AT, 0L)
        if (dismissedAt > 0) {
            val daysSinceDismiss = TimeUnit.MILLISECONDS.toDays(
                System.currentTimeMillis() - dismissedAt
            )
            if (daysSinceDismiss < config.dismissCooldownDays) return UpdateAction.None
        }

        return UpdateAction.Flexible(info)
    }
}

/** Describes the action to take based on update availability. */
sealed interface UpdateAction {
    data object None : UpdateAction
    data class Flexible(val updateInfo: AppUpdateInfo) : UpdateAction
    data class Immediate(val updateInfo: AppUpdateInfo) : UpdateAction
}
