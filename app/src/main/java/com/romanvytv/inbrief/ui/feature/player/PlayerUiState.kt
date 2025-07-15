package com.romanvytv.inbrief.ui.feature.player

data class PlayerUiState(
    val coverPath: String = "",
    val title: String = "",
    val progress: Int = 0,
    val duration: Int = 0,
    val playbackSpeed: Float = 1f,
    val isPlaying: Boolean = false
)