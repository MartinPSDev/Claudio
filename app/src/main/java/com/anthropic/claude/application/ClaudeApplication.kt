package com.anthropic.claude.application

import android.app.Application
import android.util.Log

class ClaudeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "ClaudeApplication initialized")
    }

    companion object {
        private const val TAG = "ClaudeApplication"
    }
}
