package com.example.ark_art.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ark_art.navigation.navigation_component.main_page
import com.example.ark_art.navigation.navigation_component.nestedNav

@Composable
fun navigation(){

    val listItem = listOf(
        bottomNAvigation(
            route = nestedNav.HomeRoutes.home.name,
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        bottomNAvigation(
            route = nestedNav.HomeRoutes.profile.name,
            title = "profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        )
    )

    val drawerItem = listOf(
        navigationDrawerItem(
            route = nestedNav.HomeRoutes.home.name,
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        navigationDrawerItem(
            route = nestedNav.HomeRoutes.profile.name,
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        )
    )

    var selectedItemIndext by remember { mutableIntStateOf(0) }

    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val parentRouteName = navBackStackEntry.value?.destination?.parent?.route
    val currentRouteName = listItem.any {
        it.route == parentRouteName
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxHeight(),
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(200.dp),
                    content = {
                    }
                )
                drawerItem.forEachIndexed() { index, item->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(top=5.dp),
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndext,
                        onClick = {
                            selectedItemIndext = index
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndext) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                         Row(modifier = Modifier
                             .fillMaxWidth()
                             .background(Color.White)) {
                             Text(text = "topappbar",)
                         }
                },
                bottomBar = {
                    if (currentRouteName) {
                        BottomAppBar(modifier = Modifier
                            .fillMaxWidth().background(Color.White)) {
                            Text(text = "bottom")
                        }
                        NavigationBar(
                            modifier = Modifier
                                .fillMaxWidth(),
                            content = {
                                listItem.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = parentRouteName == item.title,
                                        onClick = {
                                            selectedItemIndext = index
                                            navController.navigate(item.route)
                                        },
                                        icon = {
                                            when (item) {
                                                item -> Icon(Icons.Filled.Home, contentDescription = "home")
                                                item -> Icon(Icons.Filled.AccountCircle, contentDescription = "profile")
                                            }
                                        },
                                        label = {
                                            Text(text = item.title)
                                        }
                                    )
                                }
                            }
                        )
                    }
                },
                content = {
                    Box(
                        modifier = Modifier
                            .padding(it),
                        content = {
                            NavHost(
                                navController = navController,
                                startDestination = "Home",
                                route = "home",
                                builder = {
                                    main_page(navController = navController)
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

data class navigationDrawerItem(
    val route : String,
    val title :String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

data class bottomNAvigation(
    val route : String,
    val title : String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Preview
@Composable
fun navView() {
    navigation()
}
