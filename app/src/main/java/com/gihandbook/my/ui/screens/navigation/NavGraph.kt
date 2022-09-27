package com.gihandbook.my.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gihandbook.my.ui.screens.Screens

@Composable
fun NavGraph(navController: NavHostController) {

    val actions = remember(navController) { NavigationActions(navController) }

    NavHost(navController = navController, startDestination = Screens.Home.route) {
        homeNavGraph()
        characterNavGraph(actions)
    }
}