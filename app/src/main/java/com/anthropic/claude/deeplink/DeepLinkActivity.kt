package com.anthropic.claude.deeplink

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.anthropic.claude.mainactivity.MainActivity

class DeepLinkActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shortcutType = intent.getStringExtra(EXTRA_SHORTCUT_TYPE)
        val redirectIntent = buildRedirectIntent(shortcutType)
        startActivity(redirectIntent)
        finish()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val redirected = Intent(this.intent).apply {
            component = ComponentName(this@DeepLinkActivity, MainActivity::class.java)
            sanitizeFlags(this)
        }
        startActivity(redirected)
        finish()
    }

    private fun buildRedirectIntent(shortcutType: String?): Intent {
        return when (shortcutType) {
            SHORTCUT_TYPE_CHAT -> {
                val chatId = intent.getStringExtra(EXTRA_CHAT_ID)
                Intent(this, MainActivity::class.java).also { i ->
                    sanitizeFlags(i)
                    chatId?.let { i.putExtra(EXTRA_CHAT_ID, it) }
                }
            }
            SHORTCUT_TYPE_ARTIFACT_IN_CHAT -> {
                val chatId = intent.getStringExtra(EXTRA_CHAT_ID)
                val artifactId = intent.getStringExtra(EXTRA_ARTIFACT_IDENTIFIER)
                Intent(this, MainActivity::class.java).also { i ->
                    sanitizeFlags(i)
                    chatId?.let { i.putExtra(EXTRA_CHAT_ID, it) }
                    artifactId?.let { i.putExtra(EXTRA_ARTIFACT_IDENTIFIER, it) }
                }
            }
            else -> {
                Intent(intent).apply {
                    component = ComponentName(this@DeepLinkActivity, MainActivity::class.java)
                    sanitizeFlags(this)
                }
            }
        }
    }

    companion object {
        private const val SHORTCUT_TYPE_CHAT = "CHAT"
        private const val SHORTCUT_TYPE_ARTIFACT_IN_CHAT = "ARTIFACT_IN_CHAT"

        private const val EXTRA_SHORTCUT_TYPE = "EXTRA_SHORTCUT_TYPE"
        private const val EXTRA_CHAT_ID = "com.anthropic.claude.intent.extra.CHAT_ID"
        private const val EXTRA_ARTIFACT_IDENTIFIER = "com.anthropic.claude.intent.extra.ARTIFACT_IDENTIFIER"

        fun sanitizeFlags(intent: Intent) {
            intent.removeFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            intent.removeFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.removeFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
    }
}
