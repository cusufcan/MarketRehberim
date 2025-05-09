package com.cusufcan.marketrehberim.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cusufcan.marketrehberim.navigation.Screen.Home
import com.cusufcan.marketrehberim.navigation.Screen.Result
import com.cusufcan.marketrehberim.ui.home.HomeScreen
import com.cusufcan.marketrehberim.ui.result.ResultScreen
import com.cusufcan.marketrehberim.ui.result.ResultViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: Screen,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(350),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(350),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(350),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(350),
            )
        },
    ) {
        composable<Home> {
            HomeScreen(
                onNavigateToResult = { uri, name ->
                    navController.navigate(
                        Result(uri = uri.toString(), name = name)
                    ) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Home) {
                            saveState = true
                        }
                    }
                },
            )
        }

        composable<Result> {
            val args = it.toRoute<Result>()
            val resultViewModel: ResultViewModel = hiltViewModel()

            val uri = args.uri
            val name = args.name

            val context = LocalContext.current
            val contentResolver = context.contentResolver

            LaunchedEffect(uri, name) {
                if (uri != null) {
                    resultViewModel.fetchDataFromImage(
                        uri = uri.toUri(),
                        contentResolver = contentResolver,
                    )
                } else if (name != null) {
                    resultViewModel.fetchDataFromName(name)
                }
            }

            ResultScreen(
                resultViewModel = resultViewModel,
                navController = navController,
            )
        }
    }
}