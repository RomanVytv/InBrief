package com.romanvytv.inbrief.ui.feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romanvytv.inbrief.ASSETS_PATH
import com.romanvytv.inbrief.INTELLIGENT_INVESTOR_PATH
import com.romanvytv.inbrief.data.repo.IBookSummaryRepository
import com.romanvytv.inbrief.service.IMediaPlayerServiceConnection
import com.romanvytv.inbrief.service.MediaPlayerServiceConnection
import com.romanvytv.inbrief.ui.feature.chapters.ChaptersUiState
import com.romanvytv.inbrief.ui.feature.player.PlayerUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.roundToInt

@OptIn(ExperimentalCoroutinesApi::class)
class SummaryViewModel(
    private val repository: IBookSummaryRepository,
    private val mediaServiceConnection: IMediaPlayerServiceConnection
) : ViewModel(), KoinComponent {

    // region State

    private val _playerUiState = MutableStateFlow(PlayerUiState())
    val playerUiState: StateFlow<PlayerUiState> = _playerUiState

    private val _chaptersUiState = MutableStateFlow(ChaptersUiState())
    val chaptersUiState: StateFlow<ChaptersUiState> = _chaptersUiState

    private val _isMediaServiceConnected = MutableStateFlow(false)
    val isMediaServiceConnected: StateFlow<Boolean> = _isMediaServiceConnected

    // endregion

    private val bookSummaryPath = INTELLIGENT_INVESTOR_PATH

    // region Init & Lifecycle

    init {
        loadBookSummary()
        mediaServiceConnection.connect()

        observeMediaServiceConnection()
        observeAudioCompletion()
        observePlaybackPosition()
        observeChapterChanges()
    }

    override fun onCleared() {
        mediaServiceConnection.disconnect()
        super.onCleared()
    }

    // endregion

    // region Observers

    private fun observeMediaServiceConnection() {
        viewModelScope.launch {
            mediaServiceConnection.isConnected.collect { connected ->
                Log.d(TAG, "Media service connected: $connected")
                _isMediaServiceConnected.value = connected
            }
        }
    }

    private fun observePlaybackPosition() {
        viewModelScope.launch {
            mediaServiceConnection.isConnected
                .flatMapLatest { connected ->
                    if (connected) mediaServiceConnection.playbackPosition else emptyFlow()
                }
                .map { millis -> (millis / 1000.0).roundToInt() }
                .collect { seconds ->
                    _playerUiState.update { it.copy(progress = seconds) }
                }
        }
    }

    private fun observeAudioCompletion() {
        viewModelScope.launch {
            mediaServiceConnection.isConnected
                .flatMapLatest { connected ->
                    if (connected) mediaServiceConnection.audioCompleted else emptyFlow()
                }.collect { completed ->
                    if (completed)
                        _playerUiState.update { it.copy(progress = 0, isPlaying = false) }
                }
        }
    }

    private fun observeChapterChanges() {
        viewModelScope.launch {
            combine(
                chaptersUiState.map { it.getCurrentChapter().audioName }.distinctUntilChanged(),
                isMediaServiceConnected
            ) { audioName, isConnected -> audioName to isConnected }
                .filter { (_, isConnected) -> isConnected }
                .collect { (audioName, _) ->
                    stop()
                    _playerUiState.update { it.copy(progress = 0) }

                    val audioPath = "$bookSummaryPath/$audioName"
                    Log.d(TAG, "Preparing audio: $audioPath")
                    mediaServiceConnection.prepare(audioPath)
                }
        }
    }

    // endregion

    // region Data loading

    private fun loadBookSummary() {
        viewModelScope.launch {
            repository.getBookSummary(bookSummaryPath)?.let { summary ->
                _chaptersUiState.value = ChaptersUiState(
                    chapters = summary.chapters,
                    currentChapterId = 1
                )

                val coverPath = "$ASSETS_PATH/$bookSummaryPath/${summary.cover}"
                val duration = summary.chapters.firstOrNull()?.duration ?: 0

                _playerUiState.value = PlayerUiState(
                    coverPath = coverPath,
                    title = summary.bookTitle,
                    duration = duration
                )
            }
        }
    }

    // endregion

    // region Player actions

    fun playPause() {
        _playerUiState.update { state ->
            val shouldPlay = !state.isPlaying
            if (shouldPlay) mediaServiceConnection.play() else mediaServiceConnection.pause()
            state.copy(isPlaying = shouldPlay)
        }
    }

    fun stop() {
        _playerUiState.update { state ->
            state.copy(isPlaying = false)
        }
    }

    fun seek(progress: Int) {
        Log.d(TAG, "Seek to: $progress seconds")
        mediaServiceConnection.seekTo(progress)
    }

    fun rewind() {
        val newProgress = (_playerUiState.value.progress - SEEK_BACKWARD_SECONDS).coerceAtLeast(0)
        mediaServiceConnection.seekTo(newProgress)
    }

    fun fastForward() {
        val maxProgress = _playerUiState.value.duration
        val newProgress =
            (_playerUiState.value.progress + SEEK_FORWARD_SECONDS).coerceAtMost(maxProgress)
        mediaServiceConnection.seekTo(newProgress)
    }

    fun changePlaybackSpeed() {
        _playerUiState.update { state ->
            val nextSpeed = state.playbackSpeed.next()
            mediaServiceConnection.setSpeed(nextSpeed.speed)
            state.copy(playbackSpeed = nextSpeed)
        }
    }

    // endregion

    // region Chapter navigation

    fun nextChapter() {
        if (_chaptersUiState.value.currentChapterId == _chaptersUiState.value.chapters.size) {
            return
        }

        stop()
        _chaptersUiState.update { state ->
            val nextId = state.currentChapterId.inc().coerceAtMost(state.chapters.size)
            state.copy(currentChapterId = nextId)
        }
    }

    fun previousChapter() {
        if (_chaptersUiState.value.currentChapterId == 1) {
            return
        }

        stop()
        _chaptersUiState.update { state ->
            val prevId = state.currentChapterId.dec().coerceAtLeast(1)
            state.copy(currentChapterId = prevId)
        }
    }

    // endregion

    companion object {
        private const val TAG = "SummaryViewModel"
        private const val SEEK_FORWARD_SECONDS = 10
        private const val SEEK_BACKWARD_SECONDS = 5
    }
}
