package com.romanvytv.inbrief.ui.feature.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
        verticalArrangement = Arrangement.Bottom
    ) {

        CoverArt(playerUiState.coverPath)

        Spacer(Modifier.height(32.dp))

        KeyPointHeader(current = currentChapter.number, total = chaptersUiState.chapters.size)

        Spacer(Modifier.height(8.dp))

        Text(
            text = chaptersUiState.getCurrentChapter().keyTakeaway,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(40.dp))

        SeekBar(
            progressSeconds = playerUiState.progress,
            durationSeconds = chaptersUiState.getCurrentChapter().duration,
            onSeek = { viewModel.seek(it) }
        )

        Spacer(Modifier.height(40.dp))

        SpeedChip(speed = playerUiState.playbackSpeed)

        Spacer(Modifier.height(40.dp))

        PlayerControls(
            isPlaying = playerUiState.isPlaying,
            onPlayPause = { viewModel.playPause() },
            onRewind = { viewModel.rewind() },
            onFastForward = { viewModel.fastForward() },
            onPrevious = { viewModel.previousChapter() },
            onNext = { viewModel.nextChapter() }
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







