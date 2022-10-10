package com.gihandbook.my.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.*
import com.gihandbook.my.ui.screens.ArgumentPairs.argumentName
import com.gihandbook.my.ui.screens.ArgumentPairs.defaultValue

fun createNavArgument(vararg args: Pair<String, Any>): MutableList<NamedNavArgument> {
    val argumentList = mutableListOf<NamedNavArgument>()
    args.forEach {
        argumentList.add(navArgument(it.argumentName) {
            type = NavType.inferFromValueType(it.defaultValue)
            defaultValue = it.defaultValue
        })
    }

    return argumentList
}

fun NavBackStackEntry.getArgument(arg: Pair<String, Any>) =
    (arguments?.getParcelable(arg.argumentName) ?: arg.defaultValue).toString()

fun NavHostController.popBackWithArgs(key: String, arg: String) =
    previousBackStackEntry?.savedStateHandle?.set(key, arg).apply { popBackStack() }

@Composable
fun NavHostController.getCallback(key: String) =
    currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)?.observeAsState()?.value

fun NavBackStackEntry.getArg(key: String) = arguments?.get(key)

@Composable
fun NavBackStackEntry.getCallbackOrArg(navController: NavHostController, key: String) =
    navController.getCallback(key) ?: getArg(key)