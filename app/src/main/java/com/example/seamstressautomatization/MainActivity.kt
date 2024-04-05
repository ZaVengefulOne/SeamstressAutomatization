package com.example.seamstressautomatization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seamstressautomatization.Screens.Clothes
import com.example.seamstressautomatization.Screens.Main
import com.example.seamstressautomatization.Screens.Parts
import com.example.seamstressautomatization.Screens.Stuff
import com.example.seamstressautomatization.ui.theme.Dimens.textSizeSmall
import com.example.seamstressautomatization.ui.theme.SeamstressAutomatizationTheme
import com.example.seamstressautomatization.ui.theme.primaryGreen
import com.example.seamstressautomatization.ui.theme.quaternaryGreen
import com.example.seamstressautomatization.ui.theme.secondaryGreen
import com.example.seamstressautomatization.ui.theme.tertiaryGreen
import com.example.seamstressautomatization.utilities.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeamstressAutomatizationTheme {
               val navController = rememberNavController()
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = quaternaryGreen
                ){
                    Scaffold(
                        // Bottom navigation
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            // Navhost: where screens are placed
                            NavHostContainer(navController = navController, padding = padding)
                        })
                }
            }
        }
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
) {

    NavHost(
        navController = navController,
        // set the start destination as home
        startDestination = "main",

        // Set the padding provided by scaffold
        modifier = Modifier
            .padding(paddingValues = padding)
            .background(quaternaryGreen),

        builder = {

            // route : Home
            composable("main") {
                Main(navController = navController)
            }

            // route : search
            composable("stuff") {
                Stuff()
            }

            // route : profile
            composable("clothes") {
                Clothes()
            }

            composable("parts") {
                Parts()
            }
        })

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(

        // set background color
        backgroundColor = primaryGreen) {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        // Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->

            // Place the bottom nav items
            BottomNavigationItem(

                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,

                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },

                // Icon of navItem
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label, tint = Color.Black)
                },

                // label
                label = {
                    Text(text = navItem.label, fontSize = textSizeSmall, color = Color.Black)
                },
                alwaysShowLabel = false
            )
        }
    }
}

