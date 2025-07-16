package com.romanvytv.inbrief.service

import kotlinx.coroutines.flow.StateFlow

interface IMediaPlayerServiceConnection {
    val isConnected: StateFlow<Boolean>
    val playbackPosition: StateFlow<Int>
    val audioCompleted: StateFlow<Boolean>
     val controller: MediaPlayerController?

    fun connect()
    fun disconnect()

    fun play()
    fun pause()
    fun prepare(path: String)
    fun seekTo(positionSeconds: Int)
    fun setSpeed(speed: Float)
}