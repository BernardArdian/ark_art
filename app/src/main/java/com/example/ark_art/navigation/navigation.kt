package com.example.ark_art.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ark_art.navigation.navigation_component.main_page
import com.example.ark_art.navigation.navigation_component.nestedNav
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun navigation(
    navController: NavHostController = rememberNavController()
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }

    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        drawerItem(
            route = nestedNav.HomeRoutes.home.name,
            title = "home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        drawerItem(
            route = nestedNav.HomeRoutes.profile.name,
            title = "profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RoundedCornerShape(0.dp),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.White),
                        content = {}
                    )
                    drawerItems.forEachIndexed{index, i ->
                        NavigationDrawerItem(
                            label = {
                                    Text(text = i.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                navController.navigate(i.route)
                                selectedItemIndex = index
                                scope.launch { drawerState.close() }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex){
                                          i.selectedIcon
                                    } else{
                                          i.unselectedIcon
                                    },
                                    contentDescription = i.title
                                )
                            }
                        )
                    }
                }
            )
        },
        content = {
            Scaffold(
                bottomBar = {
                    bottomNavigation(navController)
                },
                content = {
                    NavHost(
                        modifier = Modifier.padding(bottom = 50.dp),
                        navController = navController,
                        startDestination = nestedNav.NestedRoutes.Main.name
                    ){
                        main_page(navController = navController)
                    }
                }
            )
        }
    )

}
@Composable
fun bottomNavigation(
    navController: NavHostController
){
    var home : BottomBarScreen by remember {
        mutableStateOf(
            BottomBarScreen(
                route = nestedNav.HomeRoutes.home.name,
                title = "home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            )
        )
    }
    var profile : BottomBarScreen by remember {
        mutableStateOf(
            BottomBarScreen(
                route = nestedNav.HomeRoutes.profile.name,
                title = "profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        )
    }

    val screen = listOf(
        home,
        profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Color.White
    ){
        screen.forEach{screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }

    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }

    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.unselectedIcon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        selectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.high,  red = 1f,green =0.1f,blue=0.1f),

        onClick = {
            navController.navigate(screen.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }

    )
}

data class BottomBarScreen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

data class drawerItem(
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
