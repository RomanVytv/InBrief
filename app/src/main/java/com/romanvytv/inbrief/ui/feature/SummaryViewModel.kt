package com.romanvytv.inbrief.ui.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romanvytv.inbrief.ASSETS_PATH
import com.romanvytv.inbrief.INTELLIGENT_INVESTOR_PATH
import com.romanvytv.inbrief.data.entity.BookSummary
import com.romanvytv.inbrief.data.repo.IBookSummaryRepository
import com.romanvytv.inbrief.ui.feature.player.PlayerUiState
import com.romanvytv.inbrief.ui.feature.toc.ChaptersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SummaryViewModel : ViewModel(), KoinComponent {

    private val bookSummaryPath = INTELLIGENT_INVESTOR_PATH

    private val repository: IBookSummaryRepository by inject()

    private val _bookSummary = MutableStateFlow<BookSummary?>(null)
    val bookSummary: StateFlow<BookSummary?> = _bookSummary

    private val _playerUiState = MutableStateFlow(PlayerUiState())
    val playerUiState: StateFlow<PlayerUiState> = _playerUiState

    private val _chaptersUiState = MutableStateFlow(ChaptersUiState())
    var chaptersUiState: StateFlow<ChaptersUiState> = _chaptersUiState

    init {
        loadBookSummary()
    }

    private fun loadBookSummary() {
        viewModelScope.launch {
            val summary = repository.getBookSummary(bookSummaryPath)
            _bookSummary.value = summary
            summary?.let {
                _chaptersUiState.value = ChaptersUiState(
                    chapters = it.chapters,
                    currentChapterId = 1
                )
                _playerUiState.value = _playerUiState.value.copy(
                    coverPath = "$ASSETS_PATH/$bookSummaryPath/${it.cover}",
                    title = it.bookTitle,
                    duration = chaptersUiState.value.getCurrentChapter().duration,
                )
            }
        }
    }

    // Player actions
    fun nextChapter() {

    }

    fun previousChapter() {

    }

    fun playPause() {
        _playerUiState.value =
            _playerUiState.value.copy(isPlaying = !_playerUiState.value.isPlaying)
    }

    fun seek(progress: Float) {
        _playerUiState.value = _playerUiState.value.copy(progress = progress.toInt())
    }

    fun rewind() {
        _playerUiState.value =
            _playerUiState.value.copy(progress = (_playerUiState.value.progress - 5).coerceAtLeast(0))
    }

    fun fastForward() {
        _playerUiState.value = _playerUiState.value.copy(
            progress = (_playerUiState.value.progress + 10).coerceAtMost(_playerUiState.value.duration)
        )
    }
}

