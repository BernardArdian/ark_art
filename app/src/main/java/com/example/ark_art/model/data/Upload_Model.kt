package com.example.ark_art.model.data

sealed class upload_Model{
    data class user_post(
        val id: String = "",
        val description: String = "",
        val content : String = "",
        val comment : List<post_comment> = emptyList(),
    )

    data class post_comment(
        val user_id: String = "",
        val to_id: String = "",
        val response: String = "",
    )
}
