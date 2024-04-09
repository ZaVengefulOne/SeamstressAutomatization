package com.example.seamstressautomatization.ui.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seamstressautomatization.NavHostContainer
import com.example.seamstressautomatization.ui.theme.Dimens
import com.example.seamstressautomatization.utilities.Constants

@Composable
fun Main(navController: NavController) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Главная страница", modifier = Modifier.align(Alignment.CenterHorizontally))
    }

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Constants.BottomNavItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label, tint = MaterialTheme.colorScheme.surface)
                },
                label = {
                    androidx.compose.material3.Text(text = navItem.label, fontSize = Dimens.textSizeSmall, color = MaterialTheme.colorScheme.surface)
                },
                alwaysShowLabel = false
            )
        }
    }
}