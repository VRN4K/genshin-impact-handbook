package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.data.storage.entities.WeaponEntity

interface IWeaponDBRepository {
    suspend fun addWeaponToFavorite(weapon: WeaponEntity)
    suspend fun removeWeaponFromFavorite(weaponId: String)
    suspend fun getFavoriteWeapons(): List<WeaponEntity>
}