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
    val weaponDetails: WeaponDetails,
    var isFavorite: Boolean = false
)

data class WeaponDetails(
    val passiveName: String,
    val passiveDesc: String,
    val location: String,
    val ascensionMaterial: String?
)

fun WeaponCardModel.toEntity(): WeaponEntity {
    return WeaponEntity(
        id,
        name,
        type,
        rarity,
        subStat,
        baseAttack,
        imageUrl,
        weaponDetails.passiveName,
        weaponDetails.passiveDesc,
        weaponDetails.location,
        weaponDetails.ascensionMaterial
    )
}