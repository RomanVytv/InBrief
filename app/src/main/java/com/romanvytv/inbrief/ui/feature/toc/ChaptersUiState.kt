package com.romanvytv.inbrief.ui.feature.toc

import com.romanvytv.inbrief.data.entity.Chapter

data class ChaptersUiState(
    val chapters: List<Chapter> = emptyList(),
    val currentChapterId: Int = 0
) {
    fun getCurrentChapter(): Chapter {
        return chapters.find { it.number == currentChapterId } ?: Chapter()
    }
}