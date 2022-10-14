package com.gihandbook.my.data.net.model

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