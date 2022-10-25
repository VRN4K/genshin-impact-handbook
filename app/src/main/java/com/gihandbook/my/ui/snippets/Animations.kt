package com.gihandbook.my.ui.snippets

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable

@OptIn(ExperimentalAnimationApi::class)
fun animationVerticalSideInAndOut(durationMills: Int): ContentTransform {
    return slideInVertically(
        initialOffsetY = { fullHeight -> +fullHeight },
        animationSpec = tween(durationMills)
    ) + fadeIn(animationSpec = tween(durationMills)) with slideOutVertically(
        targetOffsetY = { fullHeight -> -fullHeight },
        animationSpec = tween(durationMills)
    ) + fadeOut(animationSpec = tween(durationMills))
}

@Composable
fun SplashAnimation(isStarted: Boolean, durationMills: Int) =
    animateFloatAsState(
        targetValue = if (isStarted) 1f else 0f,
        animationSpec = tween(durationMills)
    )



