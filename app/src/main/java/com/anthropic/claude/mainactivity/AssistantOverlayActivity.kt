package com.anthropic.claude.mainactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class AssistantOverlayActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openMainInBellMode(chatId = null)
    }

    private fun openMainInBellMode(chatId: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if (chatId != null) {
                putExtra(EXTRA_START_CHAT_CHAT_ID, chatId)
                putExtra(EXTRA_START_CHAT_MODE, START_CHAT_MODE_BELL)
            }
        }
        startActivity(intent)
        finish()
    }

    companion object {
        private const val EXTRA_START_CHAT_CHAT_ID =
            "com.anthropic.claude.intent.extra.START_CHAT_CHAT_ID"
        private const val EXTRA_START_CHAT_MODE =
            "com.anthropic.claude.intent.extra.START_CHAT_MODE"
        private const val START_CHAT_MODE_BELL = "BELL_MODE"
    }
}
