package com.example.ark_art.model.data

sealed class DataState{

    class Success(val daaCollections: MutableList<apps_Model.user_post>):DataState()
    class Failure(val message : String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}
