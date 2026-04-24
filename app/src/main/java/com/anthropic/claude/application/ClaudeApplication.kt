package com.anthropic.claude.application

import android.app.Application
import android.util.Log
import com.anthropic.claude.di.AppContainer

class ClaudeApplication : Application() {

    /** Global DI container — accessible from Activities and Services. */
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        Log.i(TAG, "ClaudeApplication initialized")
    }

    companion object {
        private const val TAG = "ClaudeApplication"
    }
}
