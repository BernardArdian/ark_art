package com.example.ark_art.model.data

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.runtime.saveable.Saver
import com.google.firebase.Timestamp

sealed class apps_Model{
    data class SignIn(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val isSuccessLogin: Boolean = false,
        val loginError: String? = null,
    )
    data class SignUp(
        val userNameSignUp: String = "",
        val passwordSignUp: String = "",
        val confirmPasswordSignUp: String = "",
        val isLoading: Boolean = false,
        val signUpError: String? = null,
        val isSuccessLogin: Boolean = false,
    )

    data class user_post(
        val id: String = "",
        val description: String? = "",
        val contentCollection: List<String>?,
        val timestamp: Timestamp,
        //val comment : List<post_comment> = emptyList(),
    )

    data class post_comment(
        val user_id: String = "",
        val to_id: String = "",
        val response: String = "",
    )

    data class Home_model(
        val id : String = "",
        val description : String = "",
        val contentUrls : List<String>,
        val timestamp: Timestamp?
    )
}
