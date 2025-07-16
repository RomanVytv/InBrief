package com.romanvytv.inbrief.fakes

import com.romanvytv.inbrief.service.IMediaPlayerServiceConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMediaPlayerServiceConnection : IMediaPlayerServiceConnection {
    private val _isConnected = MutableStateFlow(true)
    override val isConnected: StateFlow<Boolean> get() = _isConnected

    private val _playbackPosition = MutableStateFlow(0)
    override val playbackPosition: StateFlow<Int> get() = _playbackPosition

    private val _audioCompleted = MutableStateFlow(false)
    override val audioCompleted: StateFlow<Boolean> get() = _audioCompleted

    var isPlaying = false
    var isPrepared = false
    var preparedPath: String? = null
    var lastSeekPosition: Int? = null
    var speedSet: Float? = null
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

    override fun play(): () -> Unit? = {
        isPlaying = true
        null
    }

    override fun pause(): () -> Unit? = {
        isPlaying = false
        null
    }

    override fun prepare(path: String): () -> Unit? = {
        preparedPath = path
        isPrepared = true
        null
    }

    override fun seekTo(positionSeconds: Int): () -> Unit? = {
        lastSeekPosition = positionSeconds
        null
    }

    override fun setSpeed(speed: Float): () -> Unit? = {
        speedSet = speed
        null
    }

    fun emitPlaybackPosition(position: Int) {
        _playbackPosition.value = position
    }

    fun emitAudioCompleted(completed: Boolean) {
        _audioCompleted.value = completed
    }
}