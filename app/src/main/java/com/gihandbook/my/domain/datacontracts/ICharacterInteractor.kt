package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.data.net.model.Character
import com.gihandbook.my.data.net.model.Enemy
import com.gihandbook.my.domain.model.CharacterUIModel
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.*

interface ICharacterInteractor {
    suspend fun getHeroByName(name: String): Character
    suspend fun getHeroesList(): List<HeroCardModel>

    suspend fun getHeroDetailInformation(name: String): CharacterUIModel
    suspend fun getEnemyDetailInformation(name: String): EnemyUIModel

    suspend fun getEnemyByName(name: String): Enemy
    suspend fun getEnemiesList(): List<EnemyCardModel>

    suspend fun getFavoritePlayableCharacters(): MutableList<HeroCardModel>
    suspend fun getFavoriteEnemiesCharacters(): MutableList<EnemyCardModel>

    suspend fun addPlayableCharacterToFavorite(character: HeroCardModel)
    suspend fun addEnemyCharacterToFavorite(character: EnemyCardModel)

    suspend fun removePlayableCharacterFromFavorite(characterId: String)
    suspend fun removeEnemyCharacterFromFavorite(characterId: String)

    suspend fun isPlayableCharacterInFavorite(characterId: String): Boolean
    suspend fun isEnemyCharacterInFavorite(characterId: String): Boolean
}