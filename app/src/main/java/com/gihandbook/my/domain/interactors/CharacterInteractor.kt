package com.gihandbook.my.domain.interactors

import android.content.res.Resources
import com.gihandbook.my.R
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
    private val charactersNetRepository: ICharacterNetRepository,
    private val resources: Resources
) : ICharacterInteractor {

    override suspend fun getHeroByName(name: String): Character {
        return charactersNetRepository.getPlayableCharacterByName(name)
    }

    override suspend fun getHeroesList(): List<CharacterCardModel> {
        val characters = mutableListOf<CharacterCardModel>()

        charactersNetRepository.getPlayableCharacters().onEach { name ->
            val character = charactersNetRepository.getPlayableCharacterByName(name)
            println(name)
            characters.add(
                character.toCardModel(
                    resources.getString(
                        R.string.character_card_image,
                        name.lowercase()
                    ),
                    resources.getString(
                        R.string.character_card_image_on_error,
                        name.lowercase()
                    ).also {
                        println(it)
                    },
                    resources.getString(
                        R.string.character_element_icon_image,
                        character.vision.lowercase()
                    )
                )
            )
        }
        return characters
    }

    override suspend fun getHeroDetailInformation(name: String): CharacterUIModel {
        return charactersNetRepository.getPlayableCharacterByName(name).toUI(resources)
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
                    resources.getString(
                        R.string.character_enemy_card_image,
                        name.lowercase()
                    ),
                    enemy.elements?.map {
                        Element(
                            it,
                            resources.getString(R.string.character_element_icon, it.lowercase())
                        )
                    } ?: emptyList()
                )
            )
        }
        return enemies
    }
}