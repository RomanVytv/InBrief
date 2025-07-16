package com.romanvytv.inbrief.fakes

import com.romanvytv.inbrief.service.IMediaPlayerServiceConnection
import com.romanvytv.inbrief.service.MediaPlayerController
import com.romanvytv.inbrief.ui.feature.player.PlaybackSpeed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMediaPlayerServiceConnection : IMediaPlayerServiceConnection {
    private val _isConnected = MutableStateFlow(true)
    override val isConnected: StateFlow<Boolean> get() = _isConnected

    private val _playbackPosition = MutableStateFlow(0)
    override val playbackPosition: StateFlow<Int> get() = _playbackPosition

    private val _audioCompleted = MutableStateFlow(false)
    override val audioCompleted: StateFlow<Boolean> get() = _audioCompleted

    override val controller: MediaPlayerController?
        get() = null

    var isPrepared = false
    var preparedPath: String? = null
    var speedSet: Float = PlaybackSpeed.DEFAULT.speed
    var connected = true
    var disconnected = false

    override fun connect() {
        connected = true
        _isConnected.value = true
    }

    override fun disconnect() {
        disconnected = true
        _isConnected.value = false
    }

    override fun play() {
        null
    }

    override fun pause() {
        null
    }

    override fun prepare(path: String) {
        preparedPath = path
        isPrepared = true
        null
    }

    override fun seekTo(positionSeconds: Int) {
    }

    override fun setSpeed(speed: Float) {
        speedSet = speed
    }
}