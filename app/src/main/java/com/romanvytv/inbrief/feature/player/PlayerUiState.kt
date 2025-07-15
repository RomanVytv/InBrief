package com.romanvytv.inbrief.feature.player

data class PlayerUiState(
    val coverUrl: String = "",
    val title: String = "",
    val currentKeyPoint: Int = 1,
    val totalKeyPoints: Int = 10,
    val progress: Int = 0,
    val duration: Int = 0,
    val playbackSpeed: Float = 1f,
    val isPlaying: Boolean = false
)