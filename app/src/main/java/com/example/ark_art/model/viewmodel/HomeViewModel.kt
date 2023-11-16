package com.example.ark_art.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ark_art.model.STORAGE_COLLECTION
import com.example.ark_art.model.repository.HomeRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepos : HomeRepository = HomeRepository()
) : ViewModel() {

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
            val urls = mutableListOf<List<String>>()
            storeCollections.value.forEach { document ->
                val collectionUrl = document[STORAGE_COLLECTION] as? List<*>
                collectionUrl?.let { urls.add(listOf(it.toString())) }
            }
            homeRepos.getStorageCollection(urls).collect{ downloadUrls ->
                _storageCollections.value = downloadUrls
            }
        }
    }

    fun likeCounter(){

    }
}
// viewmodel yang menggunakan home model
//class homevm:ViewModel() {
//    class HomeViewModel : ViewModel() {
//        private val homeRepos = HomeRepository()
//
//        private val _storeCollections = MutableStateFlow<List<Home_model>>(emptyList())
//        val storeCollections : StateFlow<List<Home_model>> = _storeCollections
//
//        private val _storageCollections = MutableStateFlow<List<Home_model>>(emptyList())
//        val storageCollections : StateFlow<List<Home_model>> = _storageCollections
//
//        fun fetchCollection(){
//            viewModelScope.launch {
//                homeRepos.getFileCollection().collect{ data ->
//                    _storeCollections.value = data
//                }
//            }
//        }
//
//        fun fetchStorageCollections(){
//            viewModelScope.launch {
//                val urls = mutableListOf<List<String>>()
//                storeCollections.value.forEach { document ->
//                    val collectionUrl = document[STORAGE_COLLECTION]
//                    document.let { urls.add(listOf(it.toString())) }
//                }
//                homeRepos.getStorageCollection(urls).collect{ downloadUrls ->
//                    _storageCollections.value = downloadUrls
//                }
//            }
//        }
//    }
//}