package com.example.ark_art.model.data

import com.google.firebase.Timestamp

sealed class apps_Model{

    sealed class authentication{
        data class SignIn(
            val username: String = "",
            val password: String = "",
            val email: String = "",
            val confirmPassword : Boolean = false,
            val content: List<user_post> = emptyList()
        )

        data class SingUp(
            val username: String = "",
            val password: String = "",
            val email: String = "",
            val confirmPassword : Boolean = false
        )
    }

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
