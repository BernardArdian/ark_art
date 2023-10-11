package com.example.ark_art.ui.view.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ark_art.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profile(){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.background(Color.Transparent)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Transparent),
                title = {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = R.drawable.backgroundprofile),
                        contentDescription = ""
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                            .background(Color.DarkGray, RoundedCornerShape(20.dp)),
                        tint= Color.Black,
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                            .background(Color.DarkGray, RoundedCornerShape(20.dp)),
                        tint= Color.Black,
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = null
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth().fillMaxHeight(),
                content = {
                    item(
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content =  {
                                    Box(
                                        modifier = Modifier
                                            .height(80.dp)
                                            .fillMaxWidth()
                                            .background(Color.LightGray),
                                        content = {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(80.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                content={
                                                    Image(
                                                        modifier = Modifier
                                                            .height(65.dp)
                                                            .width(65.dp)
                                                            .padding(5.dp)
                                                            .clip(RoundedCornerShape(40.dp)),
                                                        painter = painterResource(id = R.drawable.levi),
                                                        contentDescription = "profile picture"
                                                    )
                                                    Column(
                                                        modifier = Modifier.fillMaxHeight(),
                                                        verticalArrangement = Arrangement.Center,
                                                        content ={
                                                            Text(fontSize = 20.sp,text = "usernames")
                                                            Text(fontSize = 15.sp,text = "@names")
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    )
                                    Column(
                                        content = {
                                            Text(text = "details")
                                        }
                                    )
                                    tabs()
                                }
                            )
                        }
                    )
                    items(
                        count = 100,
                        itemContent =  {
                            Text(text = "count")
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun tabs(){
    var state by remember {
        mutableIntStateOf(0)
    }

    val title = listOf("tab 1","tab 2","tab 3")

    Column(
        modifier = Modifier,
        content = {
            TabRow(
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

@Preview(showBackground = true)
@Composable
fun profileView(){
    profile()
}