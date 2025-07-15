package com.romanvytv.inbrief.ui.feature.toc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.romanvytv.inbrief.data.entity.Chapter

@Composable
fun ChapterItem(
    chapter: Chapter,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .clickable { onSelect() },
//        colors = CardDefaults.cardColors(
//            containerColor = if (isSelected)
//                MaterialTheme.colorScheme.primaryContainer
//            else
//                MaterialTheme.colorScheme.surface
//        ),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//             Chapter number circle
//            Surface(
//                modifier = Modifier.size(40.dp),
//                shape = CircleShape,
//                color = if (isSelected)
//                    MaterialTheme.colorScheme.primary
//                else
//                    MaterialTheme.colorScheme.surfaceVariant
//            ) {
//                Box(contentAlignment = Alignment.Center) {
//                    Text(
//                        text = chapter.number.toString(),
//                        style = MaterialTheme.typography.labelLarge,
//                        color = if (isSelected)
//                            MaterialTheme.colorScheme.onPrimary
//                        else
//                            MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }
//            }
//
//            Spacer(Modifier.width(16.dp))
//
//             Chapter details
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = chapter.title,
//                    style = MaterialTheme.typography.titleMedium,
//                    color = if (isSelected)
//                        MaterialTheme.colorScheme.onPrimaryContainer
//                    else
//                        MaterialTheme.colorScheme.onSurface
//                )
//
//                Spacer(Modifier.height(4.dp))
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(
//                        text = chapter.duration,
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.secondary
//                    )
//
//                    if (chapter.isCompleted) {
//                        Spacer(Modifier.width(8.dp))
//                        Icon(
//                            Icons.Default.CheckCircle,
//                            contentDescription = "Completed",
//                            tint = MaterialTheme.colorScheme.primary,
//                            modifier = Modifier.size(16.dp)
//                        )
//                    }
//                }
//            }
//
            // Play indicator
//            if (isSelected && chapter.isPlaying) {
//                Icon(
//                    Icons.Default.PlayArrow,
//                    contentDescription = "Currently playing",
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.size(24.dp)
//                )
//            }
//        }
//    }
}
