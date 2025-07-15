package com.romanvytv.inbrief.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.romanvytv.inbrief.R
import com.romanvytv.inbrief.ui.toPainter

@Composable
fun PlayerControls(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onRewind: () -> Unit,
    onFastForward: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = onPrevious) {
            Icon(
                painter = R.drawable.ic_previous.toPainter(),
                contentDescription = "Previous chapter"
            )
        }

        IconButton(onClick = onRewind) {
            Icon(
                painter = R.drawable.ic_replay_5.toPainter(),
                contentDescription = "Rewind 5 seconds"
            )
        }

        IconButton(
            onClick = onPlayPause,
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                painter = if (isPlaying) R.drawable.ic_pause.toPainter() else R.drawable.ic_play.toPainter(),
                contentDescription = if (isPlaying) "Pause" else "Play",
                modifier = Modifier.fillMaxSize()
            )
        }

        IconButton(onClick = onFastForward) {
            Icon(
                painter = R.drawable.ic_forward_10.toPainter(),
                contentDescription = "Forward 10 seconds"
            )
        }

        IconButton(onClick = onNext) {
            Icon(painter = R.drawable.ic_next.toPainter(), contentDescription = "Next chapter")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerControlsPreview() {
    PlayerControls(
        isPlaying = false,
        onPlayPause = {},
        onRewind = {},
        onFastForward = {},
        onPrevious = {},
        onNext = {}
    )
}