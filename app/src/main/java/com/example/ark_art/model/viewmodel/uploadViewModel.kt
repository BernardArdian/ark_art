package com.example.ark_art.model.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.ark_art.model.repository.Uploadrepository
import com.google.firebase.Timestamp

class uploadViewModel(
    private val repository : Uploadrepository = Uploadrepository()
):ViewModel() {

    fun uploadPost(
        id: String,
        description: String,
        content: List<Uri>,
        timestamp: Timestamp? = null,
        onComplete: (Boolean) -> Unit
    ) {
        repository.uploadTask(id, description, content, timestamp,onComplete)
    }
}