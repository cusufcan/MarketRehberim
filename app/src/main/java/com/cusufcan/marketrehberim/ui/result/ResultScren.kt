package com.cusufcan.marketrehberim.ui.result

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    resultViewModel: ResultViewModel = hiltViewModel(),
    navController: NavController,
    uri: String?,
    name: String?,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sonuç") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                if (uri != null) {
                    Text(text = "Uri: $uri")
                } else if (name != null) {
                    Text(text = "Name: $name")
                }
            }
        }
    }
}