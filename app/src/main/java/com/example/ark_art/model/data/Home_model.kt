package com.example.ark_art.model.data

import com.google.firebase.Timestamp

data class Home_model(
    val id : String = "",
    val description : String = "",
    val contentUrls : List<String>,
    val timestamp: Timestamp?
)
