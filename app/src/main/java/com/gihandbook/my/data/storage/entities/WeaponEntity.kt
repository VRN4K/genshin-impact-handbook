package com.gihandbook.my.data.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gihandbook.my.domain.model.WeaponCardModel
import com.gihandbook.my.domain.model.WeaponDetails
import com.gihandbook.my.domain.model.WeaponType

@Entity(tableName = "weapon")
class WeaponEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: WeaponType,
    val rarity: Int,
    val subStat: String,
    val baseAttack: Int?,
    val imageUrl: String,
    val passiveName: String,
    val passiveDesc: String,
    val location: String,
    val ascensionMaterial: String?
)

fun WeaponEntity.toCardModel(): WeaponCardModel {
    return WeaponCardModel(
        id = id,
        name = name,
        type = type,
        rarity = rarity,
        subStat = subStat,
        baseAttack = baseAttack,
        imageUrl = imageUrl,
        weaponDetails = WeaponDetails(
            passiveName, passiveDesc, location, ascensionMaterial
        ),
        isFavorite = true
    )
}