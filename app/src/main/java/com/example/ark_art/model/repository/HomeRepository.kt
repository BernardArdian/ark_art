package com.example.ark_art.model.repository

import android.net.Uri
import com.example.ark_art.model.POST_COLLECTION
import com.example.ark_art.model.STORAGE_COLLECTION
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HomeRepository {

    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage : FirebaseStorage = FirebaseStorage.getInstance()

    fun getFileCollection() : Flow<List<DocumentSnapshot>>{
        return callbackFlow {
            val subscription = firestore.collection(POST_COLLECTION)
                .addSnapshotListener{snapshot, exception ->
                    if (exception != null){
                        close(exception)
                        return@addSnapshotListener
                    }
                    snapshot?.documents?.let { trySend(it).isSuccess }
                }
            awaitClose { subscription.remove() }
        }
    }

    fun getStorageCollection(url: MutableList<List<String>>) : Flow<List<String>>{

        return callbackFlow{
            val downloadTaskUrl = mutableListOf<Task<Uri>>()
            val storageRef = storage.getReference(STORAGE_COLLECTION)

            url.flatten().forEach {urls ->
                val fileRef = storageRef.child(urls.toString())
                val task = fileRef.downloadUrl
                downloadTaskUrl.add(task)
            }

            Tasks.whenAllComplete(downloadTaskUrl)
                .addOnSuccessListener {
                    val downloadUri = downloadTaskUrl.mapNotNull { task->
                        if (task.isSuccessful){
                            task.result?.toString()
                        }else{
                            null
                        }
                    }

                    trySend(downloadUri).isSuccess
                    close()
                }
                .addOnFailureListener { exception ->
                    close(exception)
                }
            awaitClose()
        }
    }
}
//repository yang menggunakan home model
//class homerepo{
//    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()
//    private val storage : FirebaseStorage = FirebaseStorage.getInstance()
//
//    fun getFileCollection() : Flow<List<Home_model>>{
//        return callbackFlow {
//            val subscription = firestore.collection(POST_COLLECTION)
//                .addSnapshotListener{snapshot, exception ->
//                    if (exception != null){
//                        close(exception)
//                        return@addSnapshotListener
//                    }
//                    snapshot?.documents?.let { trySend(it.map { snapshoot ->
//                        snapshoot.toObject(Home_model::class.java)!!
//                    }).isSuccess }
//                }
//            awaitClose { subscription.remove() }
//        }
//    }
//
//    fun getStorageCollection(url: MutableList<List<String>>) : Flow<List<Home_model>>{
//
//        return callbackFlow{
//            val downloadTaskUrl = mutableListOf<Task<Uri>>()
//            val storageRef = storage.getReference(STORAGE_COLLECTION)
//
//            url.flatten().forEach {urls ->
//                val fileRef = storageRef.child(urls.toString())
//                val task = fileRef.downloadUrl
//                downloadTaskUrl.add(task)
//            }
//
//            Tasks.whenAllComplete(downloadTaskUrl)
//                .addOnSuccessListener {
//                    val downloadUri = downloadTaskUrl.mapNotNull { task->
//                        if (task.isSuccessful){
//                            task.result?.toString()
//                        }else{
//                            null
//                        }
//                    }
//
//                    trySend(downloadUri.map { uri ->
//                        Home_model(
//                            id = "",
//                            description = "",
//                            contentUrls = listOf(uri),
//                            timestamp = Timestamp.now()
//                        )
//                    }).isSuccess
//                    close()
//                }
//                .addOnFailureListener { exception ->
//                    close(exception)
//                }
//            awaitClose()
//        }
//    }
//}