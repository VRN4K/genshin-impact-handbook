package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.data.net.model.Weapon

interface IWeaponNetRepository {
    suspend fun getAllWeapons(): List<String>
    suspend fun getWeaponDetailsByName(nameString: String): Weapon
}