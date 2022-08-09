package com.gihandbook.my.data.net.repositories

import com.gihandbook.my.data.net.model.Character
import com.gihandbook.my.data.net.model.Enemy
import com.gihandbook.my.domain.datacontracts.ICharacterNetRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.io.File
import javax.inject.Inject

class CharactersNetRepository @Inject constructor(
    private val httpClient: HttpClient
) : ICharacterNetRepository {
    override suspend fun getPlayableCharacters(): List<String> {
        return httpClient.get("/characters").body()
    }

    override suspend fun getEnemies(): List<String> {
        return httpClient.get("/enemies").body()
    }

    override suspend fun getPlayableCharacterByName(heroName: String): Character {
        return httpClient.get("/characters/$heroName").body()
    }

    override suspend fun getEnemyByName(enemyName: String): Enemy {
        return httpClient.get("/enemies/$enemyName").body()
    }

    override suspend fun getCharacterElementIcon(element: String) :String {
        return httpClient.get("/elements/${element.lowercase()}/icon").body()
    }

    override suspend fun getCharacterIcon(heroName: String) {
        val file = File("character.png")
        httpClient.get("/characters/$heroName/icon-big")
    }
}