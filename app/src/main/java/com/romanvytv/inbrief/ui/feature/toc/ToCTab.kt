package com.romanvytv.inbrief.ui.feature.toc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.romanvytv.inbrief.ui.theme.InBriefTheme

@Composable
fun ToCTab(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Text("Under construction")
    }
}

@Preview(showBackground = true)
@Composable
fun ToCTabPreview() {
    InBriefTheme {
        ToCTab(
            modifier = Modifier
        )
    }
}
