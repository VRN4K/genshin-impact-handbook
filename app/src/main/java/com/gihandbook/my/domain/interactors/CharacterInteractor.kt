package com.gihandbook.my.domain.interactors

import android.content.Context
import com.bumptech.glide.Glide
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
import javax.inject.Inject

class CharacterInteractor @Inject constructor(
    private val charactersNetRepository: ICharacterNetRepository,
    private val context: Context
) : ICharacterInteractor {

    override suspend fun getHeroByName(name: String): Character {
        return charactersNetRepository.getPlayableCharacterByName(name)
    }

    override suspend fun getHeroesList(): List<CharacterCardModel> {
        val characters = mutableListOf<CharacterCardModel>()

        charactersNetRepository.getPlayableCharacters().onEach { name ->
            println(name)
            val character = charactersNetRepository.getPlayableCharacterByName(name)
            characters.add(
                character.toCardModel(
                    context.resources.getString(
                        R.string.character_card_image,
                        name.lowercase()
                    ),
                    context.resources.getString(
                        R.string.character_card_image_on_error,
                        name.lowercase()
                    ),
                    context.resources.getString(
                        R.string.character_element_icon_image,
                        character.vision.lowercase()
                    )
                )
            )
        }
        return characters
    }

    override suspend fun getHeroDetailInformation(name: String): CharacterUIModel {
        val character = charactersNetRepository.getPlayableCharacterByName(name)
        val bitmap = Glide.with(context).asBitmap().load(
            context.resources.getString(
                R.string.character_element_icon_image,
                character.vision.lowercase()
            )
        ).submit().get()
        return character.toUI(context, bitmap)
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
                    context.resources.getString(
                        R.string.character_enemy_card_image,
                        name.lowercase()
                    ),
                    enemy.elements?.map {
                        Element(
                            it,
                            context.resources.getString(
                                R.string.character_element_icon,
                                it.lowercase()
                            )
                        )
                    } ?: emptyList()
                )
            )
        }
        return enemies
    }
}