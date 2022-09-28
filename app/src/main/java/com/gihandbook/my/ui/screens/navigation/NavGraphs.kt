package com.gihandbook.my.ui.screens.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gihandbook.my.CharactersScreen
import com.gihandbook.my.ui.screens.Screens
import com.gihandbook.my.ui.screens.characterdetailscreen.CharacterDetailsScreen
import com.google.accompanist.pager.ExperimentalPagerApi

//@OptIn(ExperimentalPagerApi::class)
//fun NavGraphBuilder.homeNavGraph() {
//    navigation(route = "characters_screen", startDestination = Screens.Home.route) {
//        composable(Screens.Home.route) {
//            CharactersScreen()
//        }
//    }
//}
//
//fun NavGraphBuilder.characterNavGraph(actions: NavigationActions) {
//    navigation(route = "character_details_screen", startDestination = Screens.Character.route) {
//        composable(Screens.Home.route) {
//            CharacterDetailsScreen(onBackButtonClick = {
//                actions.newRootScreen(Screens.Home.route)
//            })
//        }
//    }
//}