package com.example.ark_art.navigation.navigation_component

import androidx.compose.material3.Scaffold
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ark_art.model.viewmodel.AuthenticationsViewModel
import com.example.ark_art.ui.view.authentication.signin.SignIn
import com.example.ark_art.ui.view.authentication.signup.SignUp

fun NavGraphBuilder.AuthenticateNavigation(
    navController : NavHostController,
    authViewModel : AuthenticationsViewModel
) {
    navigation(
        startDestination = nestedNav.LoginRoutes.Signin.name,
        route = nestedNav.NestedRoutes.Main.name,
        builder = {
            composable(
                route = nestedNav.LoginRoutes.Signin.name,
                content = {
                    SignIn(
                        navToHomeUi = {

                        },
                        navToSignUpUi = {
                            navController.navigate(nestedNav.LoginRoutes.Signup.name)
                        }
                    )
                }
            )

            composable(
                route = nestedNav.LoginRoutes.Signup.name,
                content = {
                    SignUp()
                }
            )
        }
    )
}