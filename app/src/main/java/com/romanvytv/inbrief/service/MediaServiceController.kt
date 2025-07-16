package com.romanvytv.inbrief.service

import kotlinx.coroutines.flow.StateFlow

interface MediaPlayerController {
    fun play()
    fun pause()
    fun prepare(path: String)
    fun setSpeed(speed: Float)
    fun seekTo(seconds: Int)

    val playbackPosition: StateFlow<Int>
    val audioCompleted: StateFlow<Boolean>
}
