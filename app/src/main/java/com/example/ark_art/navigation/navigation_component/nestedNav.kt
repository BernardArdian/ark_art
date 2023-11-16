package com.example.ark_art.navigation.navigation_component

sealed class nestedNav{
    enum class LoginRoutes{
        Signup,
        Signin
    }

    enum class HomeRoutes{
        Home,
        Post,
        Profile,
    }

    enum class ProfileRoutes{
        MyMainContent,
        SavedContent
    }

    enum class NestedRoutes {
        Main,
        Login
    }
}
