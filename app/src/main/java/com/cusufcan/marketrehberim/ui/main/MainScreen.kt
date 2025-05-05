package com.cusufcan.marketrehberim.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.cusufcan.marketrehberim.ui.components.EmptyScreen
import com.cusufcan.marketrehberim.ui.components.LoadingBar
import com.cusufcan.marketrehberim.ui.main.MainContract.UiAction
import com.cusufcan.marketrehberim.ui.main.MainContract.UiEffect
import com.cusufcan.marketrehberim.ui.main.MainContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun MainScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> MainContent()
    }
}

@Composable
fun MainContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Main Content",
            fontSize = 24.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(
    @PreviewParameter(MainScreenPreviewProvider::class) uiState: UiState,
) {
    MainScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
    )
}