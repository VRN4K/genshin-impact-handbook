package com.gihandbook.my.data.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val imageUrl: String
)