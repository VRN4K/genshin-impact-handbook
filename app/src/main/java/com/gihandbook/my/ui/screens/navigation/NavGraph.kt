package com.gihandbook.my.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gihandbook.my.ui.screens.Screens
import com.gihandbook.my.ui.screens.splashscreen.AnimatedSplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    val actions = NavigationActions(navController)
//NavigationItems.Characters.route
    NavHost(navController = navController, startDestination = Screens.Splash.route) {
        composable(route = Screens.Splash.route) {
            AnimatedSplashScreen(actions)
        }
        homeNavGraph(actions)
        weaponsNavGraph(actions)
    }
}