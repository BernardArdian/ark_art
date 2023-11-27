package com.example.ark_art.navigation.navigation_component

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ModalNavigationDrawer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ark_art.ui.view.home.Home
import com.example.ark_art.ui.view.home.home_component.Upload
import com.example.ark_art.ui.view.profile.Profile

fun NavGraphBuilder.main_page(
    navController: NavHostController,
) {
    navigation(
        startDestination = nestedNav.HomeRoutes.Home.name,
        route=  nestedNav.NestedRoutes.Main_Home.name
    ){
        composable(
            route = nestedNav.HomeRoutes.Home.name,
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
                Home(
                    navigateToPost = {
                        navController.navigate(nestedNav.HomeRoutes.Post.name)
                    }
                )
            }
        )

        composable(
            route = nestedNav.HomeRoutes.Post.name,
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
                Upload(
                    navToHomePage = {
                        navController.navigate(nestedNav.HomeRoutes.Home.name)
                    }
                )
            }
        )

        composable(
            route = nestedNav.HomeRoutes.Profile.name,
            content = {
                Profile()
            }
        )
    }
}