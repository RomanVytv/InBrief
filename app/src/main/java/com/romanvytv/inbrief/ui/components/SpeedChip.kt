package com.romanvytv.inbrief.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.romanvytv.inbrief.ui.theme.backgroundColorDark

@Composable
fun SpeedChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    speed: Float
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = backgroundColorDark
    ) {
        Text(
            text = "Speed x$speed",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SpeedChipPreview() {
    SpeedChip(speed = 1.5f, onClick = {})
}