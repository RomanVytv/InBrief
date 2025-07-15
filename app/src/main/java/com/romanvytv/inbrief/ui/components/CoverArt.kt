package com.romanvytv.inbrief.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.romanvytv.inbrief.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoverArt(url: String, modifier: Modifier = Modifier) {
    if (url.isBlank()) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = modifier
                .size(240.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
    } else {
        GlideImage(
            model = url,
            contentDescription = null,
            modifier = modifier
                .size(270.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.FillHeight
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoverArtPreview() {
    CoverArt(url = "file:///android_asset/content/intelligent_investor/cover.png")
//    CoverArt(url = "")
}
