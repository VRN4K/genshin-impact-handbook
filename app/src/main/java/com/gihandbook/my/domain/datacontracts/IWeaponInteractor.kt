package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.domain.model.WeaponUIModel

interface IWeaponInteractor {
    suspend fun getAllWeapons(): List<WeaponUIModel>
}