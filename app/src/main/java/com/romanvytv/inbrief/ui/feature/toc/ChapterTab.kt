package com.romanvytv.inbrief.ui.feature.toc

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.romanvytv.inbrief.R
import com.romanvytv.inbrief.data.entity.Chapter
import com.romanvytv.inbrief.ui.feature.SummaryViewModel
import com.romanvytv.inbrief.ui.theme.InBriefTheme
import com.romanvytv.inbrief.ui.theme.backgroundColor
import com.romanvytv.inbrief.ui.toPainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChapterTab(
    modifier: Modifier = Modifier,
    viewModel: SummaryViewModel = koinViewModel(),
    sampleUiState: ChaptersUiState? = null
) {
    val realUiState by viewModel.chaptersUiState.collectAsState()
    val uiState = sampleUiState ?: realUiState

    val currentChapter = uiState.chapters.find { it.number == uiState.currentChapterId }
        ?: uiState.chapters.firstOrNull()
        ?: return

    val scrollState = rememberScrollState()


    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(24.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = currentChapter.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )

                Spacer(Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = currentChapter.keyTakeaway,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = currentChapter.text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.previousChapter() },
                modifier = Modifier
                    .background(Color.Gray, shape = RoundedCornerShape(12.dp))
                    .size(48.dp)
            ) {
                Icon(
                    painter = R.drawable.ic_arrow_left.toPainter(),
                    contentDescription = "Previous chapter",
                    tint = Color.White
                )
            }

            Text(
                modifier = modifier.padding(horizontal = 24.dp),
                text = "${currentChapter.number} of ${uiState.chapters.size}",
                color = Color.Black
            )

            IconButton(
                onClick = { viewModel.nextChapter() },
                modifier = Modifier
                    .background(Color.Gray, shape = RoundedCornerShape(12.dp))
                    .size(48.dp)
            ) {
                Icon(
                    painter = R.drawable.ic_arrow_right.toPainter(),
                    contentDescription = "Next chapter",
                    tint = Color.White
                )
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ChaptersTabPreview() {
    InBriefTheme {
        val sampleChapters = listOf(
            Chapter(1, "Intro", 120, "Intro text", "Key point 1", "audio1.mp3"),
            Chapter(2, "Chapter 2", 180, "Chapter 2 text", "Key point 2", "audio2.mp3")
        )
        val sampleUiState = ChaptersUiState(
            chapters = sampleChapters,
            currentChapterId = 1
        )

        ChapterTab(viewModel = SummaryViewModel(), sampleUiState = sampleUiState)
    }
}
