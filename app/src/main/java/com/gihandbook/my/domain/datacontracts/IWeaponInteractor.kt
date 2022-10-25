package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.data.storage.entities.WeaponEntity
import com.gihandbook.my.domain.model.WeaponCardModel

interface IWeaponInteractor {
    suspend fun getAllWeapons(): List<WeaponCardModel>

    suspend fun getFavoriteWeapons(): MutableList<WeaponCardModel>
    suspend fun removeWeaponFromFavorite(weaponId: String)
    suspend fun addWeaponToFavorite(weapon: WeaponCardModel)
}