package com.gihandbook.my.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    val actions = NavigationActions(navController)

    NavHost(navController = navController, startDestination = NavigationItems.Characters.route) {
        homeNavGraph(actions)
        weaponsNavGraph(actions)
    }
}