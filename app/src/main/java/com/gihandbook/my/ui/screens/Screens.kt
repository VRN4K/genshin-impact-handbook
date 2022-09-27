package com.gihandbook.my.ui.screens

object EndPoint {
}

object ArgumentPairs {
    val <ArgumentName> Pair<ArgumentName, *>.argumentName get() = first
    val <DefaultValue> Pair<*, DefaultValue>.defaultValue get() = second
}

object QueryParams {
    val ID = Pair("id", "0")
}

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object Character : Screens("character")
}

