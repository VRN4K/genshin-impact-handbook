package com.gihandbook.my.ui.snippets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextBlock(title: String, text: String? = null) {
    Column(modifier = Modifier.padding(top = 10.dp)) {
        Text(text = title, style = MaterialTheme.typography.h2)
        text?.let { Text(text = it, style = MaterialTheme.typography.body1) }
    }
}