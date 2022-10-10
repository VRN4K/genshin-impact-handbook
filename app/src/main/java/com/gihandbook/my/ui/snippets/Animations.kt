package com.gihandbook.my.ui.snippets

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

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



