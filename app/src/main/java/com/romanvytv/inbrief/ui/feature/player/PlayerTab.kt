package com.romanvytv.inbrief.ui.feature.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.romanvytv.inbrief.ui.components.CoverArt
import com.romanvytv.inbrief.ui.components.KeyPointHeader
import com.romanvytv.inbrief.ui.components.PlayerControls
import com.romanvytv.inbrief.ui.components.SeekBar
import com.romanvytv.inbrief.ui.components.SpeedChip
import com.romanvytv.inbrief.ui.feature.SummaryViewModel
import com.romanvytv.inbrief.ui.theme.InBriefTheme
import com.romanvytv.inbrief.ui.theme.backgroundColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListenTab(
    modifier: Modifier = Modifier,
    viewModel: SummaryViewModel = koinViewModel()
) {
    val playerUiState by viewModel.playerUiState.collectAsState()
    val chaptersUiState by viewModel.chaptersUiState.collectAsState()
    val currentChapter = chaptersUiState.getCurrentChapter()

    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.Bottom)
    ) {
        CoverArt(playerUiState.coverPath)

        KeyPointHeader(current = currentChapter.number, total = chaptersUiState.chapters.size)

        Text(
            text = currentChapter.keyTakeaway,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        SeekBar(
            progressSeconds = playerUiState.progress,
            durationSeconds = currentChapter.duration,
            onSeek = viewModel::seek
        )

        SpeedChip(
            onClick = viewModel::changePlaybackSpeed,
            speed = playerUiState.playbackSpeed.speed
        )

        PlayerControls(
            isPlaying = playerUiState.isPlaying,
            onPlayPause = viewModel::playPause,
            onRewind = viewModel::rewind,
            onFastForward = viewModel::fastForward,
            onPrevious = viewModel::previousChapter,
            onNext = viewModel::nextChapter
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ListenTabPreview() {
    InBriefTheme {
        ListenTab()
    }
}







