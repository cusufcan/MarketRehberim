package com.cusufcan.marketrehberim.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cusufcan.marketrehberim.navigation.Screen.Home
import com.cusufcan.marketrehberim.navigation.Screen.Result
import com.cusufcan.marketrehberim.ui.home.HomeScreen
import com.cusufcan.marketrehberim.ui.result.ResultScreen

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
            ResultScreen(
                navController = navController,
                uri = args.uri,
                name = args.name,
            )
        }
    }
}