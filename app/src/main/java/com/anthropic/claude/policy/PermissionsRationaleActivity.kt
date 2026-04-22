package com.anthropic.claude.policy

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

class PermissionsRationaleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openRationaleUrl()
        finish()
    }

    private fun openRationaleUrl() {
        val url = HEALTH_PERMISSIONS_RATIONALE_URL
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: ActivityNotFoundException) {
            throw IllegalArgumentException("Can't open $url.", e)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to open URI: $url", e)
        }
    }

    companion object {
        private const val TAG = "PermissionsRationale"
        private const val HEALTH_PERMISSIONS_RATIONALE_URL =
            "https://support.anthropic.com/en/articles/8942166"
    }
}
