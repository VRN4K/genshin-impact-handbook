package com.gihandbook.my.domain.interactors

import android.content.res.Resources
import com.gihandbook.my.data.net.model.toCardModel
import com.gihandbook.my.data.storage.entities.WeaponEntity
import com.gihandbook.my.data.storage.entities.toCardModel
import com.gihandbook.my.data.storage.repositories.WeaponDBRepository
import com.gihandbook.my.domain.datacontracts.IWeaponInteractor
import com.gihandbook.my.domain.datacontracts.IWeaponNetRepository
import com.gihandbook.my.domain.model.WeaponCardModel
import com.gihandbook.my.domain.model.toEntity

import javax.inject.Inject

class WeaponInteractor @Inject constructor(
    private val weaponNetRepository: IWeaponNetRepository,
    private val weaponDBRepository: WeaponDBRepository
) : IWeaponInteractor {

    @Inject
    lateinit var resources: Resources
    private val favoriteWeapons: MutableList<WeaponCardModel> = mutableListOf()

    override suspend fun getAllWeapons(): List<WeaponCardModel> {
        val weapons = mutableListOf<WeaponCardModel>()

        weaponNetRepository.getAllWeapons().forEach {
            val weapon = weaponNetRepository.getWeaponDetailsByName(it)
            weapons.add(weapon.toCardModel(it, resources))
        }

        return weapons
    }

    override suspend fun addWeaponToFavorite(weapon: WeaponCardModel) {
        favoriteWeapons.add(weapon)
        weaponDBRepository.addWeaponToFavorite(weapon.toEntity())
    }

    override suspend fun getFavoriteWeapons(): MutableList<WeaponCardModel> {
        if (favoriteWeapons.isEmpty()) favoriteWeapons.addAll(
            weaponDBRepository.getFavoriteWeapons().map { it.toCardModel() })
        return favoriteWeapons
    }

    override suspend fun removeWeaponFromFavorite(weaponId: String) {
        favoriteWeapons.remove(favoriteWeapons.find { it.id == weaponId })
        weaponDBRepository.removeWeaponFromFavorite(weaponId)
    }
}