package com.romanvytv.inbrief.feature.toc

data class Chapter(
    val id: String,
    val number: Int,
    val title: String,
    val duration: String,
    val isCompleted: Boolean = false,
    val isPlaying: Boolean = false
)

data class ChaptersUiState(
    val chapters: List<Chapter> = emptyList(),
    val currentChapterId: String? = null
)