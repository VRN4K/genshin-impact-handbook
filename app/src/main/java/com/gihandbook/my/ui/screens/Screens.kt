package com.gihandbook.my.ui.screens

import com.gihandbook.my.ui.screens.QueryParams.CHARACTER_NAME

object ArgumentPairs {
    val <ArgumentName> Pair<ArgumentName, *>.argumentName get() = first
    val <DefaultValue> Pair<*, DefaultValue>.defaultValue get() = second
}

object QueryParams {
    const val CHARACTER_NAME = "characterName"
}

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object Character : Screens("character?characterName={$CHARACTER_NAME}") {
        fun setName(name: String) =
            this.route.replace(oldValue = "{$CHARACTER_NAME}", newValue = name)
    }
}

