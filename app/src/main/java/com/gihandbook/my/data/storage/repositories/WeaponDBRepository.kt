package com.gihandbook.my.data.storage.repositories

import com.gihandbook.my.data.storage.GIDataBase
import com.gihandbook.my.data.storage.entities.WeaponEntity
import com.gihandbook.my.domain.datacontracts.IWeaponDBRepository
import javax.inject.Inject

class WeaponDBRepository @Inject constructor(private val database: GIDataBase) :
    IWeaponDBRepository {
    override suspend fun addWeaponToFavorite(weapon: WeaponEntity) {
        database.WeaponsDao().addWeapon(weapon)
    }

    override suspend fun removeWeaponFromFavorite(weaponId: String) {
        database.WeaponsDao().removeWeapon(weaponId)
    }

    override suspend fun getFavoriteWeapons(): List<WeaponEntity> {
        return database.WeaponsDao().getAllFavoriteWeapons() ?: emptyList()
    }
}