package com.gihandbook.my.domain.interactors

import android.content.res.Resources
import com.gihandbook.my.data.net.model.toCardModel
import com.gihandbook.my.data.storage.entities.WeaponEntity
import com.gihandbook.my.data.storage.repositories.WeaponDBRepository
import com.gihandbook.my.domain.datacontracts.IWeaponInteractor
import com.gihandbook.my.domain.datacontracts.IWeaponNetRepository
import com.gihandbook.my.domain.model.WeaponCardModel
import com.gihandbook.my.domain.model.WeaponUIModel
import com.gihandbook.my.domain.model.toEntity

import javax.inject.Inject

class WeaponInteractor @Inject constructor(
    private val weaponNetRepository: IWeaponNetRepository,
    private val weaponDBRepository: WeaponDBRepository
) : IWeaponInteractor {

    @Inject
    lateinit var resources: Resources
    private val favoriteWeapons: MutableList<WeaponEntity>? = null

    override suspend fun getAllWeapons(): List<WeaponCardModel> {
        val weapons = mutableListOf<WeaponCardModel>()

        weaponNetRepository.getAllWeapons().forEach {
            val weapon = weaponNetRepository.getWeaponDetailsByName(it)
            weapons.add(weapon.toCardModel(it, resources))
        }

        return weapons
    }

    override suspend fun addWeaponToFavorite(weapon: WeaponUIModel) {
        favoriteWeapons!!.add(weapon.toEntity())
        weaponDBRepository.addWeaponToFavorite(weapon.toEntity())
    }

    override suspend fun getFavoriteWeapons(): MutableList<WeaponEntity> {
        return favoriteWeapons ?: weaponDBRepository.getFavoriteWeapons().toMutableList()
    }

    override suspend fun removeWeaponFromFavorite(weaponId: String) {
        favoriteWeapons!!.remove(favoriteWeapons.find { it.id == weaponId })
        weaponDBRepository.removeWeaponFromFavorite(weaponId)
    }
}