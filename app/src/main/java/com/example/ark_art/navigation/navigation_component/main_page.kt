package com.example.ark_art.navigation.navigation_component

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ark_art.ui.view.component.upload
import com.example.ark_art.ui.view.home.home
import com.example.ark_art.ui.view.profile.profile

fun NavGraphBuilder.main_page(navController: NavHostController) {
    navigation(
        startDestination = "homepage", route="Home"){
        composable(
            route = "homepage",
            content = {
                home(
                    navigateToPost = {
                        navController.navigate("post")
                    }
                )
            }
        )

        composable(
            route = "post",
            content = {
                upload()
            }
        )

        composable(
            route = "Profile",
            content = {
                profile()
            }
        )
    }
}