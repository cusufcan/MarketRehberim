package com.cusufcan.marketrehberim.ui.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TitleText(text: String) {
    Text(
        style = MaterialTheme.typography.titleLarge,
        text = text,
    )
}