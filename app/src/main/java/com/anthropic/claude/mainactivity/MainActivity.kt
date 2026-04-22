package com.anthropic.claude.mainactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.anthropic.claude.model.IncomingPayload
import com.anthropic.claude.ui.MainScreen

class MainActivity : ComponentActivity() {

    private val intentRouter = IntentRouter()
    private var latestPayload: IncomingPayload? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            latestPayload = intentRouter.route(intent)
        }
        setContent {
            MainScreen(payload = latestPayload)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        latestPayload = intentRouter.route(intent)
        setContent {
            MainScreen(payload = latestPayload)
        }
    }

    override fun reportFullyDrawn() {
        try {
            super.reportFullyDrawn()
        } catch (e: SecurityException) {
            Log.e(TAG, "Failed to call reportFullyDrawn", e)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
