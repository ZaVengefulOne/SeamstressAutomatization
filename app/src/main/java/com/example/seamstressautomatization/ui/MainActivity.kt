package com.example.seamstressautomatization

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seamstressautomatization.Screens.StuffSetup
import com.example.seamstressautomatization.Screens.StuffViewModelFactory
import com.example.seamstressautomatization.ui.Screens.BottomNavigationBar
import com.example.seamstressautomatization.ui.Screens.ClothSetup
import com.example.seamstressautomatization.ui.Screens.ClothViewModelFactory
import com.example.seamstressautomatization.ui.Screens.Main
import com.example.seamstressautomatization.ui.Screens.MainViewModelFactory
import com.example.seamstressautomatization.ui.Screens.OperationsSetup
import com.example.seamstressautomatization.ui.Screens.OperationsViewModelFactory
import com.example.seamstressautomatization.ui.theme.SeamstressAutomatizationTheme
import com.example.seamstressautomatization.ui.viewmodels.ClothesViewModel
import com.example.seamstressautomatization.ui.viewmodels.HomeViewModel
import com.example.seamstressautomatization.ui.viewmodels.OperationsViewModel
import com.example.seamstressautomatization.ui.viewmodels.StuffViewModel

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
                            clothesViewModel = LocalViewModelStoreOwner.current?.let {
                                viewModel (
                                    it, "ClothesViewModel", ClothViewModelFactory(
                                        LocalContext.current.applicationContext as Application
                                    )
                                )
                            },
                            operationsViewModel = LocalViewModelStoreOwner.current?.let {
                                viewModel (
                                    it, "OperationsViewModel", OperationsViewModelFactory(
                                        LocalContext.current.applicationContext as Application
                                    )
                                )
                            },
                            homeViewModel = LocalViewModelStoreOwner.current?.let {
                                viewModel(
                                    it, "MainViewModel", MainViewModelFactory(
                                        LocalContext.current.applicationContext as Application
                                    )
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
    clothesViewModel: ClothesViewModel?,
    operationsViewModel: OperationsViewModel?,
    homeViewModel: HomeViewModel?,
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
                if (homeViewModel != null) {
                    Main(navController = navController, homeViewModel= homeViewModel)
                }
            }

            // route : search
            composable("stuff") {
                if (stuffViewModel != null) {
                    StuffSetup(stuffViewModel)
                }
            }

            // route : profile
            composable("clothes") {
                if (clothesViewModel != null) {
                    ClothSetup(clothesViewModel)
                }
            }

            composable("operations") {
                if (operationsViewModel != null) {
                    OperationsSetup(operationsViewModel)
                }
            }
        })

}




