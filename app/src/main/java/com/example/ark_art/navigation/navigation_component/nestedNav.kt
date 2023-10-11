package com.example.ark_art.navigation.navigation_component

sealed class nestedNav{
    enum class LoginRoutes{
        signup,
        signin
    }

    enum class HomeRoutes{
        home,
        post,
        profile,
    }

    enum class NestedRoutes {
        Main,
        Login
    }
}
