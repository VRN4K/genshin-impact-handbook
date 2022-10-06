package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.data.net.model.Character
import com.gihandbook.my.data.net.model.Enemy
import com.gihandbook.my.domain.model.CharacterCardModel
import com.gihandbook.my.domain.model.CharacterUIModel
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.*

interface ICharacterInteractor {
    suspend fun getHeroByName(name: String): Character
    suspend fun getHeroesList(): List<CharacterCardModel>
    suspend fun getHeroDetailInformation(name: String): CharacterUIModel
    suspend fun getEnemyDetailInformation(name: String): EnemyUIModel
    suspend fun getEnemyByName(name: String): Enemy
    suspend fun getEnemiesList(): List<EnemyCardModel>
}