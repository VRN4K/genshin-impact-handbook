package com.gihandbook.my.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Color.White,
    primaryVariant = Purple700,
    secondary = TextSecondaryLight,
    background = BackgroundPrimaryDark,
    onPrimary = CardColorDark,
    onSecondary = FilterChipDark
)

private val LightColorPalette = lightColors(
    primary = Color.Black,
    primaryVariant = Purple700,
    secondary = TextSecondaryDark,
    background = BackgroundPrimaryLight,
    onPrimary = CardColorLight,
    onSecondary = FilterChipLight
)

@Composable
fun GIHandbookTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {


    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}