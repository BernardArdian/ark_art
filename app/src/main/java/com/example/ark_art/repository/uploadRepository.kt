package com.example.ark_art.repository

import android.net.Uri
import com.example.ark_art.utils.storageFirebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class uploadRepository() {
    private val fireStore : CollectionReference = Firebase.firestore.collection(storageFirebase.USER_CONTENT_DB)

    fun uploadContentTask(
        onComplete : (Boolean) -> Unit
    ){
        val postId = fireStore.document().id

        fireStore.document(postId)
            .set("")
            .addOnCompleteListener { result ->

                //ketika data yan di inputkan berhalik lakukan pengecekan untuk mengupoad gambar ke dalam storage
                if (result.isSuccessful){
                    val postToStorage: FirebaseStorage = FirebaseStorage.getInstance()
                    val storage = postToStorage.reference.child("")
                    val uploaded = storage.putFile(Uri.parse(""))

                    uploaded.addOnSuccessListener {
                        storage.downloadUrl
                            .addOnSuccessListener {uri->

                            }
                            .addOnFailureListener {
                                onComplete.invoke(false)
                            }
                    }
                }
                else {
                    onComplete.invoke(false)
                }
            }
    }

    private fun multipleContentUpload(){

    }
}