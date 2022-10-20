package com.gihandbook.my.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import com.gihandbook.my.data.storage.entities.ElementConverter
import com.gihandbook.my.data.storage.entities.EnemyEntity
import com.gihandbook.my.data.storage.entities.HeroEntity

@Dao
interface CharactersDao {
    @Insert
    fun addPlayableCharacterToFavorite(heroEntity: HeroEntity)

    @Insert
    fun addEnemyCharacterToFavorite(enemyEntity: EnemyEntity)

    @Query("SELECT * FROM character_hero")
    fun getAllFavoritePlayableCharacters(): List<HeroEntity>?

    @Query("SELECT * FROM character_enemy")
    fun getAllFavoriteEnemyCharacters(): List<EnemyEntity>?

    @Query("DELETE FROM character_hero WHERE id = :characterId")
    fun deletePlayableCharacterById(characterId: String)

    @Query("DELETE FROM character_enemy WHERE id = :characterId")
    fun deleteEnemyCharacterById(characterId: String)

    @Query("SELECT * FROM character_hero WHERE id = :characterId")
    fun getPlayableCharacterById(characterId: String): HeroEntity

    @Query("SELECT * FROM character_enemy WHERE id = :characterId")
    fun getEnemyCharacterById(characterId: String): EnemyEntity
}