package com.gihandbook.my.ui.screens.favoritesscreen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.mutableStateOf
import asyncIO
import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.datacontracts.IWeaponInteractor
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.HeroCardModel
import com.gihandbook.my.domain.model.WeaponCardModel
import com.gihandbook.my.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import launchIO
import launchUI
import javax.inject.Inject

@OptIn(ExperimentalMaterialApi::class)
@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val characterInteractor: ICharacterInteractor,
    private val weaponInteractor: IWeaponInteractor
) : BaseViewModel() {

    var clickedWeapon = mutableStateOf<WeaponCardModel?>(null)
    val bottomState = ModalBottomSheetState(ModalBottomSheetValue.Hidden)

    private var heroesFromDataBase = mutableListOf<HeroCardModel>()
    private var enemiesFromDataBase = mutableListOf<EnemyCardModel>()
    private var weaponsFromDataBase = mutableListOf<WeaponCardModel>()

    val enemies = StateLiveData<MutableList<EnemyCardModel>>()
    val heroes = StateLiveData<MutableList<HeroCardModel>>()
    val weapons = StateLiveData<MutableList<WeaponCardModel>>()

    init {
        launchUI(handler) {
            heroes.postLoading()
            enemies.postLoading()
            weapons.postLoading()

            val heroesJob =
                asyncIO { characterInteractor.getFavoritePlayableCharacters() }
            val enemiesJob =
                asyncIO { characterInteractor.getFavoriteEnemiesCharacters() }
            val weaponsJob =
                asyncIO { weaponInteractor.getFavoriteWeapons() }

            heroesFromDataBase = heroesJob.await()
            enemiesFromDataBase = enemiesJob.await()
            weaponsFromDataBase = weaponsJob.await()

            heroes.postComplete(heroesFromDataBase)
            enemies.postComplete(enemiesFromDataBase)
            weapons.postComplete(weaponsFromDataBase)
        }
    }

    fun onFavoriteIconClick(cardItem: Any, listType: FavoriteListType) {
        launchIO {
            when (listType) {
                FavoriteListType.PLAYABLE_CHARACTER -> {
                    heroesFromDataBase.remove(cardItem)
                    heroes.postComplete(heroesFromDataBase)
                    characterInteractor.removePlayableCharacterFromFavorite((cardItem as HeroCardModel).id)
                }
                FavoriteListType.ENEMY_CHARACTER -> {
                    enemiesFromDataBase.remove(cardItem)
                    enemies.postComplete(enemiesFromDataBase)
                    characterInteractor.removeEnemyCharacterFromFavorite((cardItem as EnemyCardModel).id)
                }
                FavoriteListType.WEAPON -> {
                    weaponsFromDataBase.remove(cardItem)
                    weapons.postComplete(weaponsFromDataBase)
                    weaponInteractor.removeWeaponFromFavorite((cardItem as WeaponCardModel).id)
                }
            }
        }
    }

    fun onWeaponCardClicked(weapon: WeaponCardModel) {
        clickedWeapon.value = weapon
    }
}

enum class FavoriteListType {
    PLAYABLE_CHARACTER, ENEMY_CHARACTER, WEAPON
}