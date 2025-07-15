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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.romanvytv.inbrief.ui.components.CoverArt
import com.romanvytv.inbrief.ui.components.KeyPointHeader
import com.romanvytv.inbrief.ui.components.PlayerControls
import com.romanvytv.inbrief.ui.components.SpeedChip
import com.romanvytv.inbrief.ui.components.SeekBar
import androidx.compose.ui.tooling.preview.Preview
import com.romanvytv.inbrief.ui.theme.InBriefTheme
import com.romanvytv.inbrief.ui.theme.backgroundColor
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState
import com.romanvytv.inbrief.ui.feature.SummaryViewModel

@Composable
fun ListenTab(
    viewModel: SummaryViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.playerUiState.collectAsState()

    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        CoverArt(uiState.coverPath)

        Spacer(Modifier.height(32.dp))

        KeyPointHeader(current = uiState.currentKeyPoint, total = uiState.totalKeyPoints)

        Spacer(Modifier.height(8.dp))

        Text(
            text = uiState.currentKeyPointText,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(40.dp))

        SeekBar(
            progressSeconds = uiState.progress,
            durationSeconds = uiState.duration,
            onSeek = { viewModel.seek(it) }
        )

        Spacer(Modifier.height(40.dp))

        SpeedChip(speed = uiState.playbackSpeed)

        Spacer(Modifier.height(40.dp))

        PlayerControls(
            isPlaying = uiState.isPlaying,
            onPlayPause = { viewModel.playPause() },
            onRewind = { viewModel.rewind() },
            onFastForward = { viewModel.fastForward() },
            onPrevious = {},
            onNext = {}
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







