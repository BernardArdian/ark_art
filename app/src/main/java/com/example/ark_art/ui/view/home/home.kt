package com.example.ark_art.ui.view.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.ark_art.R
import com.example.ark_art.navigation.navigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun home(
    navigateToPost : ()-> Unit
){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(50.dp),
                content = {
                    Text(text = "Bottom")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = RoundedCornerShape(40.dp),
                onClick = {
                    navigateToPost.invoke()
                },
                content = {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content =  {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(4.dp),
                        content = {
                            item(
                                content = {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .background(Color.Gray),
                                        content = {
                                            Text(text = "status")
                                        }
                                    )
                                }
                            )
                            items(
                                count = 5,
                                itemContent = {
                                    contentHome()
                                }
                            )
                        }
                    )
                }
            )

            LaunchedEffect(
                key1 = "",
                block = {}
            )

        }
    )
}

@Composable
fun contentHome(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(top = 4.dp)
            .background(Color.White),
        shape = RoundedCornerShape(0.dp),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalAlignment = Alignment.CenterVertically,
                content ={
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(155.dp)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content ={
                            Image(
                                modifier = Modifier
                                    .size(55.dp)
                                    .clip(CircleShape)
                                    .border(
                                        border = BorderStroke(2.dp, Color.Blue),
                                        shape = RoundedCornerShape(40.dp)
                                    )
                                    .padding(5.dp),
                                contentScale= ContentScale.FillHeight,
                                alpha = 1f,
                                painter = painterResource(id = R.drawable.backgroundprofile),
                                contentDescription = ""
                            )

                            Column (
                                modifier = Modifier.padding(5.dp),
                                content = {
                                    Text(fontSize = 16.sp ,text="Username")
                                    Text(fontSize = 11.sp, text = "@Username")
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        modifier=Modifier.padding(5.dp),
                        content={
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = null
                            )
                        }
                    )
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                content = {
                    Column(
                        modifier =  Modifier
                            .fillMaxWidth(),
                        content =  {
                            horizontalPager()
                            Row (
                                modifier = Modifier
                                    .height(45.dp)
                                    .width(110.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        modifier = Modifier
                                            .size(35.dp)
                                            .padding(5.dp),
                                        imageVector = Icons.Filled.FavoriteBorder,
                                        contentDescription = "to react to content"
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .size(35.dp)
                                            .padding(5.dp),
                                        imageVector = Icons.Filled.MailOutline,
                                        contentDescription = "to response a content"
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .size(35.dp)
                                            .padding(5.dp),
                                        imageVector = Icons.Filled.Share,
                                        contentDescription = "to share content"
                                    )
                                }
                            )
                        }
                    )
                }
            )

            HorizontalDivider(
                modifier=Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.White
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                content= {
                    Text(text = "10/01/2028")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "10.10 AM")
                }
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun horizontalPager(){
    val state = rememberPagerState {
        +10
    }

    val context = LocalContext.current

    HorizontalPager(
        modifier= Modifier
            .fillMaxWidth()
            .height(400.dp),
        state = state,
        pageContent = {page ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.2.dp, end = 0.2.dp),
                contentAlignment = Alignment.Center,
                content = {
                    Image(
                        modifier= Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                            .clickable {
                                Toast
                                    .makeText(context, "content", Toast.LENGTH_SHORT)
                                    .show()
                            },
                        contentScale= ContentScale.FillHeight,
                        painter = painterResource(id = R.drawable.levi),
                        contentDescription = "content image"
                    )
                }
            )
        }
    )
}
