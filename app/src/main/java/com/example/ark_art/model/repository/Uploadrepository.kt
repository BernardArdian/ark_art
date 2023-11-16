package com.example.ark_art.model.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.example.ark_art.model.POST_COLLECTION
import com.example.ark_art.model.STORAGE_COLLECTION
import com.example.ark_art.model.data.apps_Model
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import kotlin.math.log

class Uploadrepository {

    val firestoreDB : CollectionReference = Firebase.firestore.collection(POST_COLLECTION)

    //firestore collection
    fun uploadTask(
        id: String,
        description: String,
        content: List<Uri?>,
        timestamp: Timestamp? = null,
        onComplete: (Boolean) -> Unit
    ){
        val postId = UUID.randomUUID().toString()

        val post = timestamp?.let { it ->
            apps_Model.user_post(
                id = postId,
                description,
                ArrayList(
                    content.mapIndexed{ index,uri ->
                        (index + 1).toString()+ " : " +uri.toString()
                    }
                ),
                it
            )
        }
        post?.let {
            firestoreDB.document(postId)
                .set(it).addOnCompleteListener { result->
                    if (result.isSuccessful){
                        contentTask(postId,content,onComplete)
                    }else{
                        onComplete(false)
                    }
                }
        }
    }

    //storage collection
    private fun contentTask(postId: String, content: List<Uri?>, onComplete: (Boolean) -> Unit){

        val storageDB = FirebaseStorage.getInstance()
        val storageTask = storageDB.reference

        val uploadedTask = mutableListOf<Task<Uri>>()

        for(
            (index,contentUri) in content.withIndex().map {
                it.index + 1 to it.value
            }
        ){
            val collectionStorage = "$STORAGE_COLLECTION/${postId}/${getTimeStampFolder()}/${index}${getFileExtension(contentUri)}"
            val storage = storageTask.child(collectionStorage)
            val uploadTasks = contentUri?.let {
                storage.putFile(it)
            }

            uploadTasks?.let {
                uploadedTask.add(
                    it.continueWithTask { task ->
                        if (!task.isSuccessful){
                            throw task.exception!!
                        }
                        storage.downloadUrl
                    }
                )
            }
        }

        //ketika konten berhasil di upload kode ini akan menambahkan field baru pada firestore berupa collectionUl
        Tasks.whenAll(uploadedTask)
            .addOnSuccessListener {
                val db = Firebase.firestore
                val documentREF = db.document("${POST_COLLECTION}/${postId}")

                val downloadUrls = uploadedTask.mapNotNull { task->
                    if (task.isSuccessful){
                        task.result
                    }else{
                        null
                    }
                }.mapIndexed{index, uri -> index +1 to uri }

                val collectionData = hashMapOf("collectionUrl" to downloadUrls)

                //untuk mengupdate field pada firestore
                documentREF.update(collectionData as Map<String, Any>)
                    .addOnSuccessListener {
                        val folderPath = "$STORAGE_COLLECTION/$postId/}"

                        getTotalFolderSize(folderPath){ totalSize->
                            if (totalSize >=0){
                                totalSize.toByte()
                            }else{
                                println("failed")
                            }
                        }
                        onComplete(true)
                    }.addOnFailureListener {
                        onComplete(false)
                    }
            }.addOnFailureListener { onComplete(false) }
    }

    private fun getTotalFolderSize(folderPath: String, onComplete: (Long) -> Unit) {
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference.child(folderPath)

        storageReference.listAll().addOnSuccessListener { listResult->
            var totalSize = 0L

            for (item in listResult.items){
                item.metadata.addOnSuccessListener { metadata->
                    totalSize += metadata.sizeBytes
                }
            }
            onComplete(totalSize)
        }.addOnFailureListener {
            onComplete(-1)
        }
    }

    companion object{
        fun uploadMultiContentToStorage(uri: Uri, context : Context, type : String){
            val storage = Firebase.storage

            val storageRef = storage.reference

            val unique_image_name = UUID.randomUUID()
            val spaceRef: StorageReference

            if (type == "image"){
                spaceRef = storageRef.child("$STORAGE_COLLECTION/$unique_image_name")
            }else{
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray = context.contentResolver
                .openInputStream(uri)
                .use { it!!.readBytes() }

            byteArray.let {
                val uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener{
                    Toast.makeText(context,"upload failed", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "upload success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    // Fungsi untuk mengambil ekstensi file dari Uri gambar
    private fun getFileExtension(uri: Uri?): String {
        val file = uri?.path?.let { File(it) }
        return MimeTypeMap.getFileExtensionFromUrl(file?.name)
    }

    // fungsi untuk mengambil date and time pada folder storage
    private fun getTimeStampFolder() : String{
        val sdf = SimpleDateFormat("yyyy-MM-dd || HH:mm:ss", Locale.getDefault())
        return sdf.format(java.util.Date())
    }
}