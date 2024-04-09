package com.example.seamstressautomatization

import android.app.Application
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
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seamstressautomatization.Screens.Stuff
import com.example.seamstressautomatization.Screens.StuffSetup
import com.example.seamstressautomatization.Screens.StuffViewModelFactory
import com.example.seamstressautomatization.ui.Screens.BottomNavigationBar
import com.example.seamstressautomatization.ui.Screens.Clothes
import com.example.seamstressautomatization.ui.Screens.Main
import com.example.seamstressautomatization.ui.Screens.Parts
import com.example.seamstressautomatization.ui.theme.Dimens.textSizeSmall
import com.example.seamstressautomatization.ui.theme.SeamstressAutomatizationTheme
import com.example.seamstressautomatization.ui.viewmodels.MainViewModel
import com.example.seamstressautomatization.ui.viewmodels.StuffViewModel
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
                        }) { padding ->
                        // Navhost: where screens are placed
                        NavHostContainer(
                            navController = navController,
                            stuffViewModel = LocalViewModelStoreOwner.current?.let {
                                viewModel(
                                    it,
                                    "StuffViewModel",
                                    StuffViewModelFactory(
                                        LocalContext.current.applicationContext
                                                as Application)
                                )
                            },
                            padding = padding
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    stuffViewModel: StuffViewModel?,
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
                if (stuffViewModel != null) {
                    StuffSetup(stuffViewModel)
                }
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




