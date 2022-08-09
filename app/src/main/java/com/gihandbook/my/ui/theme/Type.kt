package com.gihandbook.my.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gihandbook.my.R

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.quicksand)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),

    h2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.quicksand)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)