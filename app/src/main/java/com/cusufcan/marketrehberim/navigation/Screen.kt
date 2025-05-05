package com.cusufcan.marketrehberim.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class Result(val uri: String?, val name: String?) : Screen()
}