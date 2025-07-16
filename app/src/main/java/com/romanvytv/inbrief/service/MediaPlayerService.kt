package com.romanvytv.inbrief.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import com.romanvytv.inbrief.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MediaPlayerService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var mediaSession: MediaSessionCompat

    private var positionJob: Job? = null

    private val _playbackPosition = MutableStateFlow(0)

    // region Binder & Controller

    inner class MediaPlayerBinder : Binder() {
        fun getController(): MediaPlayerController = controller
    }

    private val controller = object : MediaPlayerController {
        override fun play() = mediaSession.controller.transportControls.play()
        override fun pause() = mediaSession.controller.transportControls.pause()
        override fun stop() = stopSelf()
        override fun prepare(path: String) = prepareAudio(path)
        override fun setSpeed(speed: Float) = setPlaybackSpeed(speed)
        override fun seekTo(seconds: Int) = this@MediaPlayerService.seekTo(seconds)

        override val playbackPosition: StateFlow<Int> = _playbackPosition
    }

    // endregion

    // region Lifecycle

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        setupMediaSession()
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        mediaSession.release()
        stopForeground(STOP_FOREGROUND_REMOVE)
        super.onDestroy()
    }

    // endregion

    // region Service commands

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> controller.play()
            ACTION_PAUSE -> controller.pause()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = MediaPlayerBinder()

    // endregion

    // region MediaSession & Playback

    private fun setupMediaSession() {
        mediaSession = MediaSessionCompat(this, TAG).apply {
            setCallback(object : MediaSessionCompat.Callback() {
                override fun onPlay() = play()
                override fun onPause() = pause()
            })
            isActive = true
        }
    }

    private fun prepareAudio(path: String) {
        Log.d(TAG, "prepareAudio | path=$path")

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            val afd = assets.openFd(path)
            setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            prepareAsync()
            setOnPreparedListener {
                Log.d(TAG, "OnPreparedListener | path=$path")
            }
            setOnCompletionListener { stopSelf() }
            setOnErrorListener { _, what, _ ->
                Log.e(TAG, "OnErrorListener triggered with - $what")
                stopSelf()
                true
            }
        }
    }

    private fun setPlaybackSpeed(speed: Float) {
        mediaPlayer?.let { it.playbackParams = it.playbackParams.setSpeed(speed) }
    }

    private fun play() {
        Log.d(TAG, "play")
        mediaPlayer?.start()
        startUpdatingPosition()
        updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)
        showNotification(isPlaying = true)
    }

    private fun pause() {
        Log.d(TAG, "pause")
        mediaPlayer?.pause()
        stopUpdatingPosition()
        updatePlaybackState(PlaybackStateCompat.STATE_PAUSED)
        showNotification(isPlaying = false)
    }

    private fun seekTo(seconds: Int) {
        mediaPlayer?.let { player ->
            player.seekTo((player.currentPosition + seconds * 1000).coerceIn(0, player.duration))
        }
    }

    private fun startUpdatingPosition() {
        positionJob?.cancel()
        positionJob = CoroutineScope(Dispatchers.Default).launch {
            while (mediaPlayer?.isPlaying == true) {
                _playbackPosition.update { mediaPlayer?.currentPosition ?: 0 }
                delay(500L)
            }
        }
    }

    private fun stopUpdatingPosition() {
        positionJob?.cancel()
    }

    private fun updatePlaybackState(state: Int) {
        mediaSession.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY or
                            PlaybackStateCompat.ACTION_PAUSE or
                            PlaybackStateCompat.ACTION_STOP
                )
                .setState(state, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0f)
                .build()
        )
    }

    // endregion

    // region Notification

    private fun showNotification(isPlaying: Boolean) {
        val playPauseAction = if (isPlaying) {
            NotificationCompat.Action(
                R.drawable.ic_pause, "Pause", getActionIntent(ACTION_PAUSE)
            )
        } else {
            NotificationCompat.Action(
                R.drawable.ic_play, "Play", getActionIntent(ACTION_PLAY)
            )
        }

        val style = MediaStyle()
            .setMediaSession(mediaSession.sessionToken)
            .setShowActionsInCompactView(0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setSmallIcon(R.drawable.ic_headphones)
            .addAction(playPauseAction)
            .setStyle(style)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(isPlaying)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun getActionIntent(action: String): PendingIntent {
        val intent = Intent(this, MediaPlayerService::class.java).apply { this.action = action }
        return PendingIntent.getService(
            this, action.hashCode(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID, "Media Playback", NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java)
            ?.createNotificationChannel(channel)
    }

    // endregion

    // region Constants

    companion object {
        private const val TAG = "MediaPlayerService"
        private const val CHANNEL_ID = "media_playback_channel"
        private const val NOTIFICATION_ID = 1

        private const val ACTION_PLAY = "action_play"
        private const val ACTION_PAUSE = "action_pause"
    }

    // endregion
}
