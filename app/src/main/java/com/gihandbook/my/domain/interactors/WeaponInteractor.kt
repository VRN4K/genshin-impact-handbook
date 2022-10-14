package com.gihandbook.my.domain.interactors

import android.content.res.Resources
import com.gihandbook.my.domain.datacontracts.IWeaponInteractor
import com.gihandbook.my.domain.datacontracts.IWeaponNetRepository
import com.gihandbook.my.domain.model.WeaponUIModel
import com.gihandbook.my.domain.model.toUI
import javax.inject.Inject

class WeaponInteractor @Inject constructor(
    private val weaponNetRepository: IWeaponNetRepository
) : IWeaponInteractor {

    @Inject
    lateinit var resources: Resources

    override suspend fun getAllWeapons(): List<WeaponUIModel> {
        val weapons = mutableListOf<WeaponUIModel>()

        weaponNetRepository.getAllWeapons().forEach {
            val weapon = weaponNetRepository.getWeaponDetailsByName(it)
            weapons.add(weapon.toUI(it, resources))
        }

        return weapons
    }
}