package com.gihandbook.my.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gihandbook.my.data.storage.dao.CharactersDao
import com.gihandbook.my.data.storage.dao.WeaponsDao
import com.gihandbook.my.data.storage.entities.ElementConverter
import com.gihandbook.my.data.storage.entities.EnemyEntity
import com.gihandbook.my.data.storage.entities.HeroEntity
import com.gihandbook.my.data.storage.entities.WeaponEntity

@Database(entities = [HeroEntity::class, EnemyEntity::class, WeaponEntity::class], version = 3)

@TypeConverters(ElementConverter::class)
abstract class GIDataBase : RoomDatabase() {
    abstract fun CharactersDao(): CharactersDao
    abstract fun WeaponsDao(): WeaponsDao
}