package com.gihandbook.my.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gihandbook.my.CharactersScreen
import com.gihandbook.my.ui.screens.QueryParams
import com.gihandbook.my.ui.screens.Screens
import com.gihandbook.my.ui.screens.characterdetailscreen.CharacterDetailsScreen
import com.gihandbook.my.ui.screens.enemydetailsscreen.EnemyDetailsScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    val actions = NavigationActions(navController)

    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            CharactersScreen(actions = actions)
        }
        composable(
            Screens.Character.route,
            arguments = createNavArgument(Pair(QueryParams.CHARACTER_NAME, ""))
        ) { backStackEntry ->
            CharacterDetailsScreen(
                backStackEntry.getArg(QueryParams.CHARACTER_NAME).toString(),
                onBackButtonClick = {
                    actions.backTo()
                })
        }
        composable(
            Screens.Enemy.route,
            arguments = createNavArgument(Pair(QueryParams.ENEMY_NAME, ""))
        ) { backStackEntry ->
            EnemyDetailsScreen(
                backStackEntry.getArg(QueryParams.ENEMY_NAME).toString(),
                onBackButtonClick = {
                    actions.backTo()
                })
        }
    }
}