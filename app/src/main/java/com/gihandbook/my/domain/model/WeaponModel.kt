package com.gihandbook.my.domain.model

import android.content.res.Resources
import com.gihandbook.my.R
import com.gihandbook.my.data.net.model.Weapon
import com.gihandbook.my.data.storage.entities.WeaponEntity

data class WeaponCardModel(
    val id: String,
    val name: String,
    val type: WeaponType,
    val rarity: Int,
    val subStat: String,
    val baseAttack: Int?,
    val imageUrl: String,
    val weaponDetails: WeaponDetails?
)

data class WeaponDetails(
    val passiveName: String,
    val passiveDesc: String,
    val location: String,
    val ascensionMaterial: String?
)

data class WeaponUIModel(
    val id: String,
    val name: String,
    val type: WeaponType,
    val rarity: Int,
    val subStat: String,
    val baseAttack: Int?,
    val passiveName: String,
    val passiveDesc: String,
    val location: String,
    val ascensionMaterial: String?,
    val imageUrl: String
)

fun Weapon.toUI(id: String, resources: Resources): WeaponUIModel {
    return WeaponUIModel(
        id = id,
        name = name,
        type = type,
        rarity = rarity,
        subStat = subStat,
        baseAttack = baseAttack,
        passiveName = passiveName,
        passiveDesc = passiveDesc,
        location = location,
        ascensionMaterial = ascensionMaterial,
        imageUrl = resources.getString(
            R.string.weapon_icon_url,
            id
        )
    )
}

fun WeaponUIModel.toEntity(): WeaponEntity {
    return WeaponEntity(
        id, name, type, rarity, subStat, baseAttack, imageUrl
    )
}