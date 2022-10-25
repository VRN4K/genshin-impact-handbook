package com.gihandbook.my.ui.screens

import com.gihandbook.my.ui.screens.QueryParams.CHARACTER_NAME
import com.gihandbook.my.ui.screens.QueryParams.ENEMY_NAME

object ArgumentPairs {
    val <ArgumentName> Pair<ArgumentName, *>.argumentName get() = first
    val <DefaultValue> Pair<*, DefaultValue>.defaultValue get() = second
}

object QueryParams {
    const val CHARACTER_NAME = "characterName"
    const val ENEMY_NAME = "enemyName"
}

sealed class Screens(val route: String) {
    object Splash : Screens("splash")
    object Home : Screens("home")
    object Character : Screens("character?characterName={$CHARACTER_NAME}") {
        fun setName(name: String) =
            this.route.replace(oldValue = "{$CHARACTER_NAME}", newValue = name)
    }

    object Enemy : Screens("enemy?enemyName={$ENEMY_NAME}") {
        fun setName(name: String) =
            this.route.replace(oldValue = "{$ENEMY_NAME}", newValue = name)
    }

    object Weapons : Screens("weapon")
    object Favorites : Screens("favorites")
}

