package com.gihandbook.my.data.net.repositories

import com.gihandbook.my.data.net.model.Character
import com.gihandbook.my.data.net.model.Enemy
import com.gihandbook.my.domain.datacontracts.ICharacterNetRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.File
import javax.inject.Inject

class CharactersNetRepository @Inject constructor(
    private val httpClient: HttpClient
) : ICharacterNetRepository {
    override suspend fun getPlayableCharacters(): List<String> {

        //println(httpClient.get("https://www.googleapis.com/books/v1/volumes").bodyAsText())

        return httpClient.get("https://api.genshin.dev/characters").body()
    }

    override suspend fun getEnemies(): List<String> {
        return httpClient.get("https://api.genshin.dev/enemies").body()
    }

    override suspend fun getPlayableCharacterByName(heroName: String): Character {
        return httpClient.get("https://api.genshin.dev/characters/$heroName").body()
    }

    override suspend fun getEnemyByName(enemyName: String): Enemy {
        return httpClient.get("https://api.genshin.dev/enemies/$enemyName").body()
    }
}