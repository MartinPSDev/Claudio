package com.anthropic.claude.bell.assist

import android.content.Intent
import android.speech.RecognitionService

class ClaudeRecognitionService : RecognitionService() {

    override fun onStartListening(recognizerIntent: Intent?, listener: Callback?) {
        listener?.error(ERROR_RECOGNIZER_BUSY)
    }

    override fun onCancel(listener: Callback?) {}

    override fun onStopListening(listener: Callback?) {}

    companion object {
        private const val ERROR_RECOGNIZER_BUSY = 5
    }
}
