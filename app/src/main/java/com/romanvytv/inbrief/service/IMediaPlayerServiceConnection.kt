package com.romanvytv.inbrief.service

import kotlinx.coroutines.flow.StateFlow

interface IMediaPlayerServiceConnection {
    val isConnected: StateFlow<Boolean>
    val playbackPosition: StateFlow<Int>
    val audioCompleted: StateFlow<Boolean>

    fun connect()
    fun disconnect()

    fun play(): () -> Unit?
    fun pause(): () -> Unit?
    fun prepare(path: String): () -> Unit?
    fun seekTo(positionSeconds: Int): () -> Unit?
    fun setSpeed(speed: Float): () -> Unit?
}