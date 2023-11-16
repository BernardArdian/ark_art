package com.example.ark_art.navigation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ark_art.R
import com.example.ark_art.navigation.navigation_component.main_page
import com.example.ark_art.navigation.navigation_component.nestedNav
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun NavigationContent(
    navController: NavHostController = rememberNavController()
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }

    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        DrawerItemContent(
            route = nestedNav.HomeRoutes.Home.name,
            title = "home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        DrawerItemContent(
            route = nestedNav.HomeRoutes.Profile.name,
            title = "profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                modifier= Modifier.width(310.dp),
                drawerShape = RoundedCornerShape(0.dp),
                content = {
                    Scaffold(
                        topBar = {
                            TopSideBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .height(185.dp)
                            )
                        },
                        bottomBar = {
                            val checked = remember {
                                mutableStateOf(false)
                            }

                            val icon:(@Composable ()-> Unit) = if (checked.value){
                                {
                                    Icon(
                                        painter = painterResource(id = R.drawable.brightness_on) ,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }else{
                                {
                                    Icon(
                                        painter = painterResource(id = R.drawable.brightness_off) ,
                                        contentDescription = null,
                                        tint = Color.Yellow,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                content = {
                                    val context = LocalContext.current
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        content = {
                                            Switch(
                                                modifier = Modifier,
                                                checked = checked.value,
                                                onCheckedChange = { isChecked ->
                                                    checked.value = isChecked.apply {
                                                        Toast.makeText(context,"theme", Toast.LENGTH_SHORT).show()
                                                    }
                                                },
                                                thumbContent = icon,
                                                colors = SwitchDefaults.colors(
                                                    checkedThumbColor = Color.Transparent,
                                                    checkedTrackColor = Color.DarkGray,
                                                    checkedIconColor = Color.Black,
                                                    uncheckedThumbColor = Color.Yellow
                                                )
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Icon(
                                                imageVector = Icons.TwoTone.Settings,
                                                contentDescription = "setting's"
                                            )
                                        }
                                    )
                                }
                            )
                        },
                        content = {
                            Spacer(modifier = Modifier.padding(200.dp))
                            Column(
                                modifier = Modifier
                                    .padding(
                                        top = 205.dp,
                                        start = 11.dp,
                                        end = 11.dp,
                                        bottom = 55.dp
                                    )
                                    .fillMaxHeight(),
                                content = {
                                    HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.White)
                                    drawerItems.forEachIndexed{index, i ->
                                        NavigationDrawerItem(
                                            shape= RoundedCornerShape(5.dp),
                                            modifier = Modifier
                                                .padding(top = 5.dp)
                                            ,
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
                        }
                    )
                }
            )
        },
        content = {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController)
                },
                content = {
                    NavHost(
                        modifier = Modifier.padding(bottom = 45.dp),
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TopSideBar(
    modifier : Modifier = Modifier,
){
    Column(
        modifier = modifier,
        content =  {
            Row (
                modifier= Modifier
                    .height(60.dp)
                    .width(160.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                content = {
                    Image(
                        modifier = Modifier
                            .size(55.dp)
                            .clip(RoundedCornerShape(50.dp)),
                        painter=painterResource(id = R.drawable.levi),
                        contentDescription = "profile image"
                    )

                    Text(text = "Username")
                }
            )

            Text(text = "following")
            Text(text = "followers")
        }
    )
}
@Composable
fun BottomNavigationBar(
    navController: NavHostController
){
    val home : BottomBarScreen by remember {
        mutableStateOf(
            BottomBarScreen(
                route = nestedNav.HomeRoutes.Home.name,
                title = "home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            )
        )
    }
    val profile : BottomBarScreen by remember {
        mutableStateOf(
            BottomBarScreen(
                route = nestedNav.HomeRoutes.Profile.name,
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
        modifier= Modifier,
    ){
        screen.forEach{screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.selectedIcon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        selectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium,  red = 1f,green =1f,blue= 1f),

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

data class DrawerItemContent(
    val route : String,
    val title :String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Preview
@Composable
fun NavView() {
    NavigationContent()
}
