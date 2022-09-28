package com.gihandbook.my.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gihandbook.my.ui.screens.Screens
import com.gihandbook.my.ui.screens.characterdetailscreen.CharacterDetailsScreen

@Composable
fun NavGraph(navController: NavHostController) {

    val actions = remember(navController) { NavigationActions(navController) }

    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Character.route) {
            CharacterDetailsScreen(onBackButtonClick = {
                actions.backTo()
            })
        }

//        homeNavGraph()
//        characterNavGraph(actions)
    }
}