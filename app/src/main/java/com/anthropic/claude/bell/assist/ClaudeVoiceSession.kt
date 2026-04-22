package com.anthropic.claude.bell.assist

import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession

class ClaudeVoiceSession(context: Context) : VoiceInteractionSession(context) {

    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)
        hide()
    }
}
