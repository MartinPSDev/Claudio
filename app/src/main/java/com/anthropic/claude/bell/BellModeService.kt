package com.anthropic.claude.bell

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.anthropic.claude.R
import com.anthropic.claude.mainactivity.MainActivity

class BellModeService : Service() {

    private val binder = LocalBinder()
    private var mediaSession: MediaSession? = null
    private var chatId: String? = null

    override fun onBind(intent: Intent): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        chatId = intent?.getStringExtra(EXTRA_CHAT_ID)
        ensureForeground()
        resetMediaSession()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession?.isActive = false
        mediaSession?.release()
        mediaSession = null
    }

    private fun ensureForeground() {
        val notification = buildNotification()
        try {
            startForeground(NOTIFICATION_ID, notification)
        } catch (_: Exception) {
            stopSelf()
        }
    }

    private fun resetMediaSession() {
        mediaSession?.isActive = false
        mediaSession?.release()
        mediaSession = MediaSession(this, "BellModeVoice").apply {
            isActive = true
            setPlaybackState(
                PlaybackState.Builder()
                    .setState(PlaybackState.STATE_PAUSED, -1L, 1f)
                    .setActions(
                        PlaybackState.ACTION_PLAY or
                            PlaybackState.ACTION_PAUSE or
                            PlaybackState.ACTION_STOP,
                    )
                    .build(),
            )
        }
    }

    private fun buildNotification(): Notification {
        val manager = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Voice Mode",
                NotificationManager.IMPORTANCE_LOW,
            )
            manager.createNotificationChannel(channel)
        }

        val mainIntent = Intent(this, MainActivity::class.java).apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            chatId?.let { putExtra(EXTRA_CHAT_ID, it) }
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            REQUEST_CODE_OPEN_APP,
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.voice_mode_notification_title))
            .setOngoing(true)
            .setSilent(true)
            .setContentIntent(pendingIntent)
            .build()
    }

    inner class LocalBinder : Binder() {
        fun service(): BellModeService = this@BellModeService
    }

    companion object {
        private const val CHANNEL_ID = "voice_mode_notification_channel"
        private const val NOTIFICATION_ID = 500
        private const val REQUEST_CODE_OPEN_APP = 500
        const val EXTRA_CHAT_ID = "com.anthropic.claude.intent.extra.CHAT_ID"
    }
}
