package com.gihandbook.my.domain.datacontracts

import com.gihandbook.my.data.net.model.Character
import com.gihandbook.my.data.net.model.Enemy

interface ICharacterNetRepository {
    suspend fun getPlayableCharacters(): List<String>
    suspend fun getEnemies(): List<String>
    suspend fun getPlayableCharacterByName(heroName: String): Character
    suspend fun getEnemyByName(enemyName: String): Enemy

    suspend fun getCharacterElementIcon(element: String): String
    suspend fun getCharacterIcon(heroName: String)
}