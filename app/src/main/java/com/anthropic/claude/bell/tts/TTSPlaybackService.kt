package com.anthropic.claude.bell.tts

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.anthropic.claude.R

class TTSPlaybackService : Service() {

    private var mediaSession: MediaSession? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action

        when (action) {
            ACTION_UPDATE_STATE -> {
                val isPlaying = intent.getBooleanExtra(EXTRA_IS_PLAYING, true)
                updatePlaybackState(isPlaying)
                return START_STICKY
            }
            ACTION_STOP -> {
                val callback = stopCallback
                if (callback != null) {
                    callback.invoke()
                } else {
                    stopSelf()
                }
                return START_STICKY
            }
        }

        ensureForeground()
        mediaSession?.isActive = false
        mediaSession?.release()
        mediaSession = null

        val session = MediaSession(this, "TTSPlayback")
        session.isActive = true
        mediaSession = session
        updatePlaybackState(isPlaying = true)
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
        if (Build.VERSION.SDK_INT >= 31) {
            try {
                startForeground(NOTIFICATION_ID, notification)
            } catch (e: Exception) {
                stopSelf()
                return
            }
        } else if (Build.VERSION.SDK_INT >= 30) {
            startForeground(NOTIFICATION_ID, notification)
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun updatePlaybackState(isPlaying: Boolean) {
        val state = if (isPlaying) PlaybackState.STATE_PLAYING else PlaybackState.STATE_PAUSED
        val playbackState = PlaybackState.Builder()
            .setState(state, -1L, 1f)
            .setActions(0L)
            .build()
        mediaSession?.setPlaybackState(playbackState)
    }

    private fun buildNotification(): Notification {
        val manager = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(CHANNEL_ID, "TTS Playback", NotificationManager.IMPORTANCE_LOW)
            )
        }

        val openAppComponent = ComponentName(packageName, "com.anthropic.claude.deeplink.DeepLinkActivity")
        val openIntent = Intent(Intent.ACTION_VIEW).apply {
            component = openAppComponent
            flags = 0x30000000
        }
        val openPendingIntent = PendingIntent.getActivity(
            this, REQUEST_CODE_OPEN_APP, openIntent,
            0x0c000000 or PendingIntent.FLAG_IMMUTABLE,
        )

        val stopPendingIntent = PendingIntent.getService(
            this, REQUEST_CODE_STOP_TTS,
            Intent(this, TTSPlaybackService::class.java).setAction(ACTION_STOP),
            0x0c000000 or PendingIntent.FLAG_IMMUTABLE,
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.tts_notification_title))
            .setOngoing(true)
            .setSilent(true)
            .setContentIntent(openPendingIntent)
            .addAction(0, getString(R.string.tts_notification_stop), stopPendingIntent)
            .build()
    }

    companion object {
        private const val CHANNEL_ID = "tts_playback_notification_channel"
        private const val NOTIFICATION_ID = 0x1f5
        private const val REQUEST_CODE_OPEN_APP = 0x1f5
        private const val REQUEST_CODE_STOP_TTS = 0x1f6

        const val ACTION_STOP = "com.anthropic.claude.tts.STOP"
        const val ACTION_UPDATE_STATE = "com.anthropic.claude.tts.UPDATE_STATE"
        const val EXTRA_IS_PLAYING = "is_playing"

        @Volatile
        var stopCallback: (() -> Unit)? = null
    }
}
