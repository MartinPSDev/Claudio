package com.anthropic.claude.app.trusteddevice

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * Persists the trusted device token obtained during enrollment.
 * The token is stored in [EncryptedSharedPreferences] for security.
 */
class TrustedDeviceStore(
    private val prefs: SharedPreferences,
) {
    companion object {
        private const val PREFS_NAME = "trusted_device_prefs"
        private const val KEY_DEVICE_TOKEN = "device_token"

        fun from(context: Context): TrustedDeviceStore {
            val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            val prefs = EncryptedSharedPreferences.create(
                PREFS_NAME,
                masterKey,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
            )
            return TrustedDeviceStore(prefs)
        }
    }

    /** Returns the saved device token, or null if not enrolled. */
    val deviceToken: String?
        get() = prefs.getString(KEY_DEVICE_TOKEN, null)

    /** Whether this device is currently enrolled as trusted. */
    val isEnrolled: Boolean
        get() = deviceToken != null

    /** Saves the device token received from the server. */
    fun saveToken(token: String) =
        prefs.edit { putString(KEY_DEVICE_TOKEN, token) }

    /** Removes the device token (e.g. on logout or un-enrollment). */
    fun clearToken() =
        prefs.edit { remove(KEY_DEVICE_TOKEN) }
}
