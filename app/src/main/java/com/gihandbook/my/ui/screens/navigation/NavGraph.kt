package com.gihandbook.my.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gihandbook.my.CharactersScreen
import com.gihandbook.my.ui.screens.Screens
import com.gihandbook.my.ui.screens.characterdetailscreen.CharacterDetailsScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import javax.inject.Inject

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph(navController: NavHostController) {

    val actions = NavigationActions(navController)

    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            CharactersScreen()
        }
        composable(Screens.Character.route) {
            CharacterDetailsScreen(onBackButtonClick = {
                actions.backTo()
            })
        }

//        homeNavGraph()
//        characterNavGraph(actions)
    }
}