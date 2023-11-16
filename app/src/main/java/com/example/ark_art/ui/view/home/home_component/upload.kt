package com.example.ark_art.ui.view.home.home_component

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ark_art.model.viewmodel.uploadViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.ark_art.model.data.apps_Model
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Upload(
    viewModel: uploadViewModel = viewModel(),
    navToHomePage: () -> Unit,
){
    var post by remember {
        mutableStateOf(
            apps_Model.user_post(
                id = "",
                description = "",
                contentCollection = ArrayList(),
                timestamp = Timestamp.now()
            )
        )
    }
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var isPost by remember { mutableStateOf(false) }
    var contentCollection by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val scope = rememberCoroutineScope { Dispatchers.IO }

    val pickContentCollections = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            if (uris.isNotEmpty()) {
                contentCollection = uris
            }
        }
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    pickContentCollections.launch("image/*") },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Open gallery from device"
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(top=5.dp, end = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            DisposableEffect(
                                key1 = Unit,
                                effect = {
                                    onDispose {
                                        keyboardController?.hide()
                                    }
                                }
                            )
                            LaunchedEffect(
                                key1 = Unit,
                                block = {
                                    focusRequester.requestFocus()
                                }
                            )

                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester)
                                    .then(
                                        Modifier.padding(5.dp)
                                    )
                                    .background(
                                        Color.Black,
                                        RoundedCornerShape(10.dp)
                                    ),
                                shape= RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                ),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(35.dp)
                                            .clip(CircleShape)
                                            .background(Color.Blue)
                                    )
                                },
                                value = post.description.toString(),
                                onValueChange = { values ->
                                    post = post.copy(description = values)
                                },
                                label = {
                                    Text(text = "Tell your story")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                ),
                            )

                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    if (contentCollection.isNotEmpty()){
                        LazyRow(
                            content = {
                                items(contentCollection.size){index->
                                    val uri = contentCollection[index]
                                    AsyncImage(
                                        model = uri,
                                        modifier = Modifier
                                            .height(400.dp)
                                            .fillMaxWidth(),
                                        contentScale = ContentScale.None,
                                        contentDescription = "content image"
                                    )
                                }
                            }
                        )

                        Button(
                            onClick = {
                                isPost = true
                                navToHomePage.invoke()
                            },
                            content = {
                                Text("Post")
                            }
                        )
                    }
                }
            )
            LaunchedEffect(
                key1 = isPost,
                block = {
                    if (isPost && contentCollection.isNotEmpty()) {
                        scope.launch {
                            viewModel.uploadPost(
                                post.id,
                                post.description.toString(),
                                contentCollection,
                                Timestamp.now()
                            ) { result ->
                                val message = if (result) "Uploaded..." else "Failed to upload..."
                                Log.d("LaunchedEffect", "Showing Toast message: $message")
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                            }
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun textViewInput(
    story : String,
    onStoryChange : (String) -> Unit
){
    TextField(value = story, onValueChange = onStoryChange)
}