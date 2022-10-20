package com.gihandbook.my.data.net.model

import android.content.res.Resources
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.WeaponCardModel
import com.gihandbook.my.domain.model.WeaponDetails
import com.gihandbook.my.domain.model.WeaponType
import kotlinx.serialization.Serializable

@Serializable
data class Weapon(
    val name: String,
    val type: WeaponType,
    val rarity: Int,
    val subStat: String,
    val baseAttack: Int?,
    val passiveName: String,
    val passiveDesc: String,
    val location: String,
    val ascensionMaterial: String?
)

fun Weapon.toCardModel(id: String, resources: Resources): WeaponCardModel {
    return WeaponCardModel(
        id = id,
        name = name,
        type = type,
        rarity = rarity,
        subStat = subStat,
        baseAttack = baseAttack,
        weaponDetails = WeaponDetails(
            passiveName = passiveName,
            passiveDesc = passiveDesc,
            location = location,
            ascensionMaterial = ascensionMaterial
        ),
        imageUrl = resources.getString(
            R.string.weapon_icon_url,
            id
        )
    )
}