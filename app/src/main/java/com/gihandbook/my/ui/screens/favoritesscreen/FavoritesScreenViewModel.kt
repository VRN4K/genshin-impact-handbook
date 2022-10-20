package com.gihandbook.my.ui.screens.favoritesscreen

import asyncIO
import com.gihandbook.my.data.storage.entities.WeaponEntity
import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.datacontracts.IWeaponInteractor
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.HeroCardModel
import com.gihandbook.my.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import launchIO
import launchUI
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val characterInteractor: ICharacterInteractor,
    private val weaponInteractor: IWeaponInteractor
) : BaseViewModel() {

    private var heroesFromDataBase = mutableListOf<HeroCardModel>()
    private var enemiesFromDataBase = mutableListOf<EnemyCardModel>()
    private var weaponsFromDataBase = mutableListOf<WeaponEntity>()

    val enemies = StateLiveData<MutableList<EnemyCardModel>>()
    val heroes = StateLiveData<MutableList<HeroCardModel>>()
    val weapons = StateLiveData<MutableList<WeaponEntity>>()

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
                    characterInteractor.removePlayableCharacterFromFavorite(cardItem as HeroCardModel)
                }
                FavoriteListType.ENEMY_CHARACTER -> {
                    enemiesFromDataBase.remove(cardItem)
                    enemies.postComplete(enemiesFromDataBase)
                    characterInteractor.removeEnemyCharacterFromFavorite(cardItem as EnemyCardModel)
                }
                FavoriteListType.WEAPON -> {
                    weapons.value?.data?.remove(cardItem)
                    characterInteractor.removeEnemyCharacterFromFavorite(cardItem as EnemyCardModel)
                }
            }
        }
    }
}

enum class FavoriteListType {
    PLAYABLE_CHARACTER, ENEMY_CHARACTER, WEAPON
}