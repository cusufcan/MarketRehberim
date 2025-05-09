package com.cusufcan.marketrehberim.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cusufcan.marketrehberim.common.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    resultViewModel: ResultViewModel,
    navController: NavController,
) {
    val resultList by resultViewModel.resultList.collectAsState()

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
        },
    ) { padding ->

        if (resultList is Resource.Loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 12.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(resultList.value ?: emptyList()) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .sizeIn(maxHeight = 96.dp),
                        onClick = {},
                    ) {
                        Row {
                            AsyncImage(
                                modifier = Modifier.size(96.dp),
                                model = it.image,
                                contentDescription = null,
                            )
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                            ) {
                                Row {
                                    ItemTitleText(
                                        modifier = Modifier.weight(1f),
                                        name = it.name,
                                        textAlign = TextAlign.Start,
                                    )
                                    ItemTitleText(
                                        name = it.price,
                                        textAlign = TextAlign.End,
                                    )
                                }
                                ItemSubtitleText(text = it.market)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemTitleText(
    modifier: Modifier = Modifier,
    name: String,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = name,
        style = MaterialTheme.typography.titleSmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun ItemSubtitleText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        style = MaterialTheme.typography.labelMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}