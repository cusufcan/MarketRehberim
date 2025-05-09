package com.cusufcan.marketrehberim.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResultItem(
    val name: String,
    val price: String,
    val image: String,
    val market: String,
)