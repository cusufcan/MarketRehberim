package com.cusufcan.marketrehberim.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TextIconButton(
    icon: ImageVector,
    contentDescription: String,
    text: String,
    space: Dp = 12.dp,
    size: Dp = 48.dp,
    iconSize: Dp = 24.dp,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space),
    ) {
        FilledIconButton(
            modifier = Modifier.size(size),
            onClick = onClick,
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = icon,
                contentDescription = contentDescription,
            )
        }
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = text,
        )
    }
}