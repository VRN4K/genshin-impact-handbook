package com.gihandbook.my.data.storage.repositories

import com.gihandbook.my.data.storage.GIDataBase
import com.gihandbook.my.data.storage.entities.EnemyEntity
import com.gihandbook.my.data.storage.entities.HeroEntity
import com.gihandbook.my.domain.datacontracts.ICharacterDBRepository
import javax.inject.Inject

class CharactersDBRepository @Inject constructor(private val database: GIDataBase) :
    ICharacterDBRepository {
    override fun addPlayableCharacterToFavorite(heroEntity: HeroEntity) {
        database.CharactersDao().addPlayableCharacterToFavorite(heroEntity)
    }

    override fun addEnemyCharacterToFavorite(enemyEntity: EnemyEntity) {
        database.CharactersDao().addEnemyCharacterToFavorite(enemyEntity)
    }

    override fun getAllFavoritePlayableCharacters(): List<HeroEntity> {
        return database.CharactersDao().getAllFavoritePlayableCharacters() ?: emptyList()
    }

    override fun getAllFavoriteEnemyCharacters(): List<EnemyEntity> {
        return database.CharactersDao().getAllFavoriteEnemyCharacters() ?: emptyList()
    }

    override fun deletePlayableCharacterById(characterId: String) {
        database.CharactersDao().deletePlayableCharacterById(characterId)
    }

    override fun deleteEnemyCharacterById(characterId: String) {
        database.CharactersDao().deleteEnemyCharacterById(characterId)
    }

    override fun getPlayableCharacterById(characterId: String): HeroEntity {
        return database.CharactersDao().getPlayableCharacterById(characterId)
    }

    override fun getEnemyCharacterById(characterId: String): EnemyEntity {
        return database.CharactersDao().getEnemyCharacterById(characterId)
    }

}