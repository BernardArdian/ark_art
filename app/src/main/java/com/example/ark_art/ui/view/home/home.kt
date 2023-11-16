package com.example.ark_art.ui.view.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ark_art.model.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.ark_art.R
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@Composable
fun Home(
    navigateToPost: () -> Unit,
    viewModel: HomeViewModel = viewModel(),
){
    val firestoreData by viewModel.storeCollections.collectAsState()
    val storageUrls by viewModel.storageCollections.collectAsState()

    LaunchedEffect(
        key1 = viewModel.storeCollections,
        block = {
            viewModel.fetchCollection()
        }
    )
    LaunchedEffect(
        key1 = viewModel.storageCollections,
        block = {
            viewModel.fetchStorageCollections()
        }
    )

    Scaffold(
        modifier = Modifier
            .padding(bottom = 15.dp),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = {
                    Spacer(modifier = Modifier.weight(0.04f))
                    Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = null)
                    Spacer(modifier = Modifier.weight(0.01f))
                    Text(text = "Top Bar")
                    Spacer(modifier = Modifier.weight(1f))
                }
            )
        },
        floatingActionButton = {
            IconButton(
                modifier = Modifier
                    .background(
                        Color.White,
                        RoundedCornerShape(40.dp)
                    )
                    .border(1.dp, Color.Blue, RoundedCornerShape(40.dp)),
                onClick = { navigateToPost.invoke() },
                content = {
                    Icon(
                        modifier= Modifier,
                        tint = Color.Black,
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            )
        },
        content = { paddingValues ->
            val scrollState = rememberScrollState ()
            Column(
                modifier = Modifier
                    .padding(paddingValues),
                content = {
                    val context = LocalContext.current

                    HomeTabs(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                Toast
                                    .makeText(context, "hello", Toast.LENGTH_SHORT)
                                    .show()
                            },
                    )

                    LazyColumn(
                        modifier = Modifier
                            .scrollable(
                                state = scrollState,
                                orientation = Orientation.Vertical,
                                enabled = true
                            ),
                        content =  {
                            items(
                                items=firestoreData,
                                key = {document ->
                                    document.id
                                },
                                itemContent = { document ->
                                    val timeStamp = document.getTimestamp("timestamp")
                                    val date = timeStamp?.toDate()
                                    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                    val formattedDate = dateFormat.format(date!!)

                                    val imageUrl = listOf("${storageUrls.size}")

                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(top = 5.dp),
                                        content ={
                                            ContentCollections(
                                                modifier= Modifier.clickable {
                                                    //navigateToPost.invoke()
                                                    Toast.makeText(context,document["description"] as String,Toast.LENGTH_SHORT).show()
                                                },
                                                dateTime = formattedDate,
                                                descriptions = document["description"] as String,
                                                imageUrl = imageUrl
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun HomeTabs(
    modifier: Modifier = Modifier
){
    var state by remember {
        mutableIntStateOf(0)
    }

    val title = listOf("home","following")

    Column(
        modifier = modifier,
        content = {
            PrimaryTabRow(
                selectedTabIndex =  state,
                tabs = {
                    title.forEachIndexed{index,item ->
                        Tab(
                            text = { Text(text = item) },
                            selected = state == index,
                            onClick = { state = index }
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun ContentCollections(
    modifier : Modifier = Modifier,
//    moreIcon : () -> Unit,
    dateTime: String,
    descriptions: String,
    imageUrl : List<String>
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(590.dp)
            .background(Color.Blue),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray),
                content ={
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White),
                        content ={
                            Row(
                                modifier = Modifier
                                    .width(180.dp)
                                    .padding(start = 5.dp),
                                content = {
                                    Icon(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(50.dp),
                                        imageVector = Icons.TwoTone.AccountCircle,
                                        tint = Color.Black ,
                                        contentDescription = "profile for user who post content"
                                    )
                                    Column (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 5.dp,
                                                end = 5.dp
                                            ),
                                        verticalArrangement = Arrangement.SpaceEvenly,
                                        content = {
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                color= Color.Black,
                                                text = "username"
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                color= Color.Black,
                                                fontSize = 12.sp,
                                                text = dateTime
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                    Column(
                        modifier = modifier,
                        content = {

//                            Image(
//                                painter = painterResource(id = R.drawable.levi),
//                                contentDescription =""
//                            )
                        }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .height(150.dp),
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                content ={
                                    Row(
                                        modifier = Modifier
                                            .padding(start = 5.dp, end = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        content ={
                                            Icon(
                                                imageVector = Icons.Outlined.FavoriteBorder,
                                                contentDescription = "like's",
                                                tint = Color.Black
                                            )
                                            Icon(
                                                imageVector = Icons.Outlined.Share,
                                                contentDescription = "like's",
                                                tint = Color.Black
                                            )
                                        }
                                    )
                                    Row {
                                        Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "")
                                    }
                                }
                            )
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness =  1.dp,
                                color = Color.Black
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .fillMaxHeight(),
                                content = {
                                    Text(
                                        modifier= Modifier,
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                        text = "15.k"
                                    )
                                    Text(
                                        modifier= Modifier
                                            .fillMaxSize(),
                                        color = Color.Black,
                                        text = descriptions
                                    )
                                }
                            )
                            Column(
                                modifier = Modifier,
                                content = {

                                    Text(
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                        text = dateTime
                                    )
                                }
                            )
                        }
                    )
                    Column(
                        modifier = modifier,
                        content = {
                            LazyColumn {
                                items(imageUrl) { image ->
                                    Image(
                                    painter = rememberAsyncImagePainter(
                                        model = image
                                    ),
                                    contentDescription = "content image uploaded by user",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp)
                                        .padding(4.dp)
                                )
                                }
                            }
//                            Image(
//                                painter = rememberAsyncImagePainter(model = imageUrl),
//                                contentDescription =""
//                            )
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness =  1.dp,
                        color = Color.Black
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalContent(){

    val image = rememberAsyncImagePainter(model = painterResource(id = R.drawable.levi))

    val painter  = remember{
        mutableStateListOf("$image")
    }

    val list = listOf(
        painter
    )
    val state = rememberPagerState{
        list.size
    }

    HorizontalPager(
        state = state,
        pageContent = {
            AsyncImage(
                model = state,
                contentDescription = ""
            )
        }
    )
}

@Preview
@Composable
fun HomePreview(){
    val imgUri = listOf("")
    ContentCollections(
        descriptions = "sample",
        dateTime = "1/2/2022",
        imageUrl = imgUri
    )
}