package com.comye1.itunessearch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * 정사각 이미지
 * @param imgUrl 웹 이미지 주소
 * @param description 접근성 지원을 위한 이미지 설명
 */
@Composable
fun SquareImage(
    modifier: Modifier = Modifier,
    imgUrl: String?,
    description: String?
) {
    Box(
        modifier = modifier.aspectRatio(1f)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .crossfade(true)
                .build(),
            contentDescription = description,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }
}


@Preview
@Composable
fun SquareImagePreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SquareImage(
            modifier = Modifier.fillMaxWidth(.3f),
            imgUrl = "",
            description = "이미지",
        )
        Spacer(modifier = Modifier.height(16.dp))
        SquareImage(
            modifier = Modifier.fillMaxWidth(.4f),
            imgUrl = "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
            description = "이미지",
        )
    }
}