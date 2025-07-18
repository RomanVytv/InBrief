package com.romanvytv.inbrief

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.romanvytv.inbrief.ui.feature.player.ListenTab
import com.romanvytv.inbrief.ui.feature.chapters.ChapterTab
import com.romanvytv.inbrief.ui.pxToDp
import com.romanvytv.inbrief.ui.theme.InBriefTheme
import com.romanvytv.inbrief.ui.theme.backgroundColor
import com.romanvytv.inbrief.ui.theme.primaryColor
import com.romanvytv.inbrief.ui.theme.textColor

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Player", "ToC")
    val icons = listOf(R.drawable.ic_headphones, R.drawable.ic_toc)

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .padding(vertical = 30.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .width(120.dp)
                        .border(2.pxToDp(), textColor, RoundedCornerShape(50)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    tabs.forEachIndexed { index, _ ->
                        val isSelected = selectedTab == index
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) primaryColor else Color.Transparent)
                                .clickable { selectedTab = index },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(icons[index]),
                                contentDescription = null,
                                tint = if (selectedTab == index) Color.White else Color.Black
                            )
                        }
                        if (index == 0)
                            Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> ListenTab(modifier = Modifier)
                1 -> ChapterTab(modifier = Modifier)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    InBriefTheme {
        MainScreen()
    }
}

