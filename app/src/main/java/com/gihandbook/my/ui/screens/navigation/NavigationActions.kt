package com.gihandbook.my.ui.screens.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

class NavigationActions(private val navController: NavController) {

    val navigateUpTo: () -> Unit = {
        navController.navigateUp()
    }

    val backTo: () -> Unit = {
        navController.popBackStack()
    }

    val newRootScreen: (route: String) -> Unit = {
        navController.navigate(it) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }

    val navigateTo: (route: String) -> Unit = {
        navController.navigate(it)
    }


    var currentRoute: () -> String? = {
        navController.currentBackStackEntry?.destination?.route
    }

}