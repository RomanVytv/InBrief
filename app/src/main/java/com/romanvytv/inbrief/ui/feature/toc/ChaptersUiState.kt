package com.romanvytv.inbrief.ui.feature.toc

import com.romanvytv.inbrief.data.entity.Chapter

data class ChaptersUiState(
    val chapters: List<Chapter> = emptyList(),
    val currentChapterId: String? = null
)