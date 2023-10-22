package com.example.ark_art.model.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ark_art.model.POST_COLLECTION
import com.example.ark_art.model.STORAGE_COLLECTION
import com.example.ark_art.model.repository.HomeRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val homeRepos = HomeRepository()

    private val _storeCollections = MutableStateFlow<List<DocumentSnapshot>>(emptyList())
    val storeCollections : StateFlow<List<DocumentSnapshot>> = _storeCollections

    private val _storageCollections = MutableStateFlow<List<String>>(emptyList())
    val storageCollections : StateFlow<List<String>> = _storageCollections

    fun fetchCollection(){
        viewModelScope.launch {
            homeRepos.getFileCollection().collect{ data ->
                _storeCollections.value = data
            }
        }
    }

    fun fetchStorageCollections(){
        viewModelScope.launch {
            val urls = mutableListOf<String>()
            storeCollections.value.forEach { document ->
                val collectionUrl = document[STORAGE_COLLECTION] as? List<*>

                collectionUrl?.let { urls.add(it.toString()) }
            }
            homeRepos.getStorageCollection(urls).collect{ downloadUrls ->
                _storageCollections.value = downloadUrls
            }
        }
    }
}