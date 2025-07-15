package com.romanvytv.inbrief.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SeekBar(
    progressSeconds: Int,
    durationSeconds: Int,
    onSeek: (Float) -> Unit
) {
    val progress = progressSeconds.toFloat() / durationSeconds.coerceAtLeast(1)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = formatTime(progressSeconds),
            style = MaterialTheme.typography.labelSmall
        )

        Slider(
            value = progress,
            onValueChange = { onSeek(it) },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.Blue,
                activeTrackColor = Color.Blue,
                inactiveTrackColor = Color.LightGray
            )
        )

        Text(
            text = formatTime(durationSeconds),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun formatTime(seconds: Int): String {
    val safeSeconds = seconds.coerceAtLeast(0)
    val minutes = safeSeconds / 60
    val secs = safeSeconds % 60
    return "%02d:%02d".format(minutes, secs)
}

@Preview(showBackground = true)
@Composable
fun SeekBarPreview() {
    SeekBar(
        progressSeconds = 50,
        durationSeconds = 180,
        onSeek = {}
    )
}