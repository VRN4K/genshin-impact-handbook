package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.data.storage.entities.EnemyEntity
import com.gihandbook.my.data.storage.entities.HeroEntity

interface ICharacterDBRepository {

    fun addPlayableCharacterToFavorite(heroEntity: HeroEntity)
    fun addEnemyCharacterToFavorite(enemyEntity: EnemyEntity)

    fun getAllFavoritePlayableCharacters(): List<HeroEntity>
    fun getAllFavoriteEnemyCharacters(): List<EnemyEntity>

    fun deletePlayableCharacterById(characterId: String)
    fun deleteEnemyCharacterById(characterId: String)

    fun getPlayableCharacterById(characterId: String): HeroEntity
    fun getEnemyCharacterById(characterId: String): EnemyEntity
}