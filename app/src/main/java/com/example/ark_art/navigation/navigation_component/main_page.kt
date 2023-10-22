package com.example.ark_art.navigation.navigation_component

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ark_art.model.viewmodel.uploadViewModel
import com.example.ark_art.ui.view.component.upload
import com.example.ark_art.ui.view.home.home
import com.example.ark_art.ui.view.profile.profile

fun NavGraphBuilder.main_page(
    navController: NavHostController,
) {

    navigation(
        startDestination = nestedNav.HomeRoutes.home.name,
        route=  nestedNav.NestedRoutes.Main.name
    ){
        composable(
            route = nestedNav.HomeRoutes.home.name,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(500)
                )
            },
            content = {
                home(
                    navigateToPost = {
                        navController.navigate(nestedNav.HomeRoutes.post.name)
                    }
                )
            }
        )

        composable(
            route = nestedNav.HomeRoutes.post.name,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {

                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500),
                )

            },
            content = {
                upload(
                    navToHomePage = {
                        navController.navigate(nestedNav.HomeRoutes.home.name)
                    }
                )
            }
        )

        composable(
            route = nestedNav.HomeRoutes.profile.name,
            content = {
                profile()
            }
        )
    }
}