package com.gihandbook.my.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gihandbook.my.data.storage.entities.WeaponEntity

@Dao
interface WeaponsDao {
    @Insert
    fun addWeapon(weaponEntity: WeaponEntity)

    @Query("DELETE FROM weapon WHERE id = :weaponId")
    fun removeWeapon(weaponId: String)

    @Query("SELECT * FROM weapon")
    fun getAllFavoriteWeapons(): List<WeaponEntity>?

}