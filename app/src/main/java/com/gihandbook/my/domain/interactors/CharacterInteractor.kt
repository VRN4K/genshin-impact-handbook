package com.gihandbook.my.domain.interactors

import com.gihandbook.my.data.net.model.Character
import com.gihandbook.my.data.net.model.Enemy
import com.gihandbook.my.data.net.model.toCardModel
import com.gihandbook.my.data.net.model.toUI
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.datacontracts.ICharacterNetRepository
import com.gihandbook.my.domain.model.CharacterCardModel
import com.gihandbook.my.domain.model.CharacterUIModel
import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.HeroCardModel
import javax.inject.Inject

class CharacterInteractor @Inject constructor(
    private val charactersNetRepository: ICharacterNetRepository
) : ICharacterInteractor {

    override suspend fun getHeroByName(name: String): Character {
        return charactersNetRepository.getPlayableCharacterByName(name)
    }

    override suspend fun getHeroesList(): List<HeroCardModel> {
        val characters = mutableListOf<HeroCardModel>()


    override suspend fun getHeroesList(): List<CharacterCardModel> {
        val characters = mutableListOf<CharacterCardModel>()

        charactersNetRepository.getPlayableCharacters().onEach { name ->
            val character = charactersNetRepository.getPlayableCharacterByName(name)

            characters.add(
                character.toCardModel(
                    "https://api.genshin.dev/characters/$name/icon-big",
                    "https://api.genshin.dev/characters/$name/icon",
                    "https://api.genshin.dev/elements/${character.vision.lowercase()}/icon"
                )
            )
        }
        return characters
    }

    override suspend fun getHeroDetailInformation(name: String): CharacterUIModel {
        return charactersNetRepository.getPlayableCharacterByName(name).toUI()
    }

    override suspend fun getEnemyByName(name: String): Enemy {
        return charactersNetRepository.getEnemyByName(name)
    }

    override suspend fun getEnemiesList(): List<EnemyCardModel> {
        val enemies = mutableListOf<EnemyCardModel>()
        charactersNetRepository.getEnemies().onEach { name ->
            val enemy = charactersNetRepository.getEnemyByName(name)
            enemies.add(
                enemy.toCardModel(
                    "https://api.genshin.dev/enemies/$name/icon",
                    enemy.elements?.map {
                        Element(
                            it,
                            "https://api.genshin.dev/elements/${it.lowercase()}/icon"
                        )
                    } ?: emptyList()
                )
            )
        }
        return enemies
    }
}