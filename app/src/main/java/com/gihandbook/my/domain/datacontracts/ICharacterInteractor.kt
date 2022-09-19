package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.data.net.model.Character
import com.gihandbook.my.data.net.model.Enemy
import com.gihandbook.my.domain.model.CharacterCardModel
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.HeroCardModel

interface ICharacterInteractor {
    suspend fun getHeroByName(name: String): Character
    suspend fun getHeroesList(): List<HeroCardModel>

    suspend fun getEnemyByName(name: String): Enemy
    suspend fun getEnemiesList(): List<EnemyCardModel>
}