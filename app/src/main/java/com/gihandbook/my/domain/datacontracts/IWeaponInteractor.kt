package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.data.storage.entities.WeaponEntity
import com.gihandbook.my.domain.model.WeaponCardModel
import com.gihandbook.my.domain.model.WeaponUIModel

interface IWeaponInteractor {
    suspend fun getAllWeapons(): List<WeaponCardModel>

    suspend fun addWeaponToFavorite(weapon: WeaponUIModel)
    suspend fun getFavoriteWeapons(): MutableList<WeaponEntity>
    suspend fun removeWeaponFromFavorite(weaponId: String)
}