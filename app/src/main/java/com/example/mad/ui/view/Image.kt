package com.example.mad.ui.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun Image(
    url:String,
    modifier: Modifier
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier
    ) {
        val state = painter.state
        if (
            state is AsyncImagePainter.State.Loading ||
            state is AsyncImagePainter.State.Error
        ) {
            ImageShimmer(modifier = modifier)
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}

@Composable
private fun ImageShimmer(
    modifier: Modifier
){
    val brush = rememberShimmer()

    Spacer(
        modifier = modifier
            .background(brush)
    )
}


@Composable
private fun rememberShimmer(): Brush {
    val shimmerColors = listOf(
        Color.DarkGray.copy(alpha = 0.6f),
        Color.DarkGray.copy(alpha = 0.2f),
        Color.DarkGray.copy(alpha = 0.6f),
    )
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )
}