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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
import com.example.seamstressautomatization.utilities.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SeamstressAutomatizationTheme {
               val navController = rememberNavController()
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
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
            .background(colorScheme.background),

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
        backgroundColor = colorScheme.primary) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Constants.BottomNavItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label, tint = colorScheme.surface)
                },
                label = {
                    Text(text = navItem.label, fontSize = textSizeSmall, color = colorScheme.surface)
                },
                alwaysShowLabel = false
            )
        }
    }
}

