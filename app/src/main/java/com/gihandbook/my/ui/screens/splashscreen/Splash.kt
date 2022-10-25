package com.gihandbook.my.ui.screens.splashscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gihandbook.my.R
import com.gihandbook.my.domain.extensions.forwardingPainter
import com.gihandbook.my.ui.screens.navigation.NavigationActions
import com.gihandbook.my.ui.screens.navigation.NavigationItems
import com.gihandbook.my.ui.snippets.SplashAnimation
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(actions: NavigationActions) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = SplashAnimation(isStarted = startAnimation, durationMills = 3000)

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        actions.newRootScreen(NavigationItems.Characters.route)
    }
    Splash(alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = forwardingPainter(
                painterResource(id = R.drawable.logo),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            ), contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .alpha(alpha)
        )
    }
}