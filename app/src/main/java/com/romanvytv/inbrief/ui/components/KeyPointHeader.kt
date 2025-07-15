package com.romanvytv.inbrief.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun KeyPointHeader(current: Int, total: Int) {
    Text(
        text = "KEY POINT $current OF $total",
        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.2.sp),
        color = MaterialTheme.colorScheme.secondary,
    )
}

@Preview(showBackground = true)
@Composable
fun KeyPointHeaderPreview() {
    KeyPointHeader(current = 2, total = 5)
}