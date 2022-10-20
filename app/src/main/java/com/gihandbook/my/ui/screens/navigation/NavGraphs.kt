package com.gihandbook.my.ui.screens.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gihandbook.my.ui.screens.QueryParams
import com.gihandbook.my.ui.screens.Screens
import com.gihandbook.my.ui.screens.characterdetailscreen.CharacterDetailsScreen
import com.gihandbook.my.ui.screens.charactersscreen.CharactersScreen
import com.gihandbook.my.ui.screens.enemydetailsscreen.EnemyDetailsScreen
import com.gihandbook.my.ui.screens.favoritesscreen.FavoritesScreen
import com.gihandbook.my.ui.screens.favoritesscreen.FavoritesScreenViewModel
import com.gihandbook.my.ui.screens.weaponsscreen.WeaponsScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.homeNavGraph(actions: NavigationActions) {
    navigation(
        route = NavigationItems.Characters.route,
        startDestination = Screens.Home.route
    ) {
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
            Screens.Favorites.route,
        ) {
            FavoritesScreen(actions = actions)
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


fun NavGraphBuilder.weaponsNavGraph(actions: NavigationActions) {
    navigation(route = NavigationItems.Weapons.route, startDestination = Screens.Weapons.route) {
        composable(Screens.Weapons.route) {
            WeaponsScreen(actions = actions)
        }
    }
}
