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

@Composable
fun ListenTab(
    uiState: PlayerUiState,
    onPlayPause: () -> Unit,
    onSeek: (Float) -> Unit,
    onRewind: () -> Unit,
    onFastForward: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        CoverArt(uiState.coverUrl)

        Spacer(Modifier.height(32.dp))

        KeyPointHeader(current = uiState.currentKeyPoint, total = uiState.totalKeyPoints)

        Spacer(Modifier.height(8.dp))

        Text(
            text = uiState.title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(40.dp))

        SeekBar(
            progressSeconds = uiState.progress,
            durationSeconds = uiState.duration,
            onSeek = onSeek
        )

        Spacer(Modifier.height(40.dp))

        SpeedChip(speed = uiState.playbackSpeed)

        Spacer(Modifier.height(40.dp))

        PlayerControls(
            isPlaying = uiState.isPlaying,
            onPlayPause = onPlayPause,
            onRewind = onRewind,
            onFastForward = onFastForward,
            onPrevious = {},
            onNext = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListenTabPreview() {
    InBriefTheme {
        ListenTab(
            uiState = PlayerUiState(
                coverUrl = "",
                title = "Sample Title",
                currentKeyPoint = 2,
                totalKeyPoints = 5,
                progress = 5,
                duration = 120,
                playbackSpeed = 1.25f,
                isPlaying = false
            ),
            onPlayPause = {},
            onSeek = {},
            onRewind = {},
            onFastForward = {}
        )
    }
}







