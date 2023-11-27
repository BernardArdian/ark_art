package com.example.ark_art.ui.view.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ark_art.R
import com.example.ark_art.model.viewmodel.HomeViewModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    viewModel : HomeViewModel = HomeViewModel()
){

    val firestoreData by viewModel.storeCollections.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(
        key1 = viewModel.storageCollections,
        block = {
            viewModel.fetchStorageCollections()
        }
    )

    Scaffold(
        modifier = Modifier
            .background(Color.Transparent)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = {
                    Column(
                        content = {
                            Text(text = "TopBar")
                            Text(text = "sample")
                        }
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
                        imageVector = Icons.Sharp.KeyboardArrowLeft,
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
                    .fillMaxWidth()
                    .fillMaxHeight(),
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
                                }
                            )
                        }
                    )

                   items(
                       items = firestoreData,
                       key = {document ->
                           document.id
                       },
                       itemContent = {document ->
                           profileTabsPage(
                               viewModel,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                description = document["description"] as String
                            )
                       }
                   )
                }
            )
        }
    )
}

@SuppressLint("ComposableNaming")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun profileTabsPage(
    viewModel: HomeViewModel = HomeViewModel(),
    modifier: Modifier = Modifier,
    description: String
){

    val tabItem = listOf(
        TabItem(Icons.Outlined.Menu,Icons.Filled.Menu),
        TabItem(Icons.Outlined.FavoriteBorder,Icons.Filled.Favorite)
    )

    val pagerState = rememberPagerState(
        initialPage = tabItem.lastIndex,
        pageCount = {
            tabItem.size
        }
    )

    var selectedTebIndex by remember {
        mutableIntStateOf(0)
    }

    val coroutineScope = rememberCoroutineScope()
    
    LaunchedEffect(
        key1 = selectedTebIndex,
        block = {
            pagerState.animateScrollToPage(selectedTebIndex)
        }
    )

    LaunchedEffect(
        key1 = pagerState.currentPage,
        key2 = pagerState.isScrollInProgress,
        block = {
            if (!pagerState.isScrollInProgress){
                selectedTebIndex = pagerState.currentPage
            }
        }
    )

    PrimaryTabRow(
        modifier = modifier,
        selectedTabIndex =  selectedTebIndex,
        indicator = {tabPositions: List<TabPosition> ->
            val modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTebIndex])
            Box(
                modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
            )
        },
        tabs = {
            tabItem.forEachIndexed{index,item ->
                Tab(
                    selected = selectedTebIndex == index,
                    onClick = {
                        selectedTebIndex = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(selectedTebIndex)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = (if (index == selectedTebIndex) item.selectedTabIcon else item.unselectedTabIcon) as ImageVector,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    )

    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        state = pagerState,
        pageContent = { page->
            when(page){
                0 -> PostPage(
                    viewModel = viewModel,
                    description = description
                )
                1 -> SavePage()
            }
        }
    )
}

@Composable
fun PostPage(
    viewModel: HomeViewModel = HomeViewModel(),
    description : String
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
            .padding(bottom = 10.dp)
            .background(Color.Gray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Text(text = description)
        }
    )
}


@Composable
fun SavePage(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
            .padding(bottom = 10.dp)
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Text(text = "Saved Ui Page")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileView(){
    Profile()
}