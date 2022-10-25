package com.gihandbook.my.domain.interactors

import android.content.res.Resources
import androidx.palette.graphics.Palette
import com.bumptech.glide.RequestManager
import com.gihandbook.my.R
import com.gihandbook.my.data.net.model.*
import com.gihandbook.my.data.storage.entities.toCardModel
import com.gihandbook.my.domain.datacontracts.ICharacterDBRepository
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.datacontracts.ICharacterNetRepository
import com.gihandbook.my.domain.model.CharacterUIModel
import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.*
import javax.inject.Inject

class CharacterInteractor @Inject constructor(
    private val charactersNetRepository: ICharacterNetRepository,
    private val charactersDBRepository: ICharacterDBRepository
) : ICharacterInteractor {

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var resources: Resources

    private var favoritePlayableCharacters: MutableList<HeroCardModel> = mutableListOf()
    private val favoriteEnemyCharacters: MutableList<EnemyCardModel> = mutableListOf()

    override suspend fun getHeroByName(name: String): Character {
        return charactersNetRepository.getPlayableCharacterByName(name)
    }

    override suspend fun getHeroesList(): List<HeroCardModel> {
        getFavoritePlayableCharacters()
        val characters = mutableListOf<HeroCardModel>()


        charactersNetRepository.getPlayableCharacters().onEach { name ->
            val character = charactersNetRepository.getPlayableCharacterByName(name)

            characters.add(
                character.toCardModel(
                    name,
                    resources.getString(R.string.character_card_image, name),
                    resources.getString(R.string.character_card_image_on_error, name),
                    resources.getString(
                        R.string.character_element_icon_image,
                        character.vision.lowercase()
                    ),
                    isPlayableCharacterInFavorite(name)
                )
            )
        }
        return characters
    }

    override suspend fun getHeroDetailInformation(name: String): CharacterUIModel {
        val character = charactersNetRepository.getPlayableCharacterByName(name)

        val elementBitmap = glide.asBitmap().load(
            resources.getString(R.string.character_element_icon_image, character.vision.lowercase())
        ).submit().get()

        return character.toUI(name, resources, Palette.from(elementBitmap).generate())
    }

    override suspend fun getEnemyDetailInformation(name: String): EnemyUIModel {
        return charactersNetRepository.getEnemyByName(name).toUIModel(resources)
    }

    override suspend fun getEnemyByName(name: String): Enemy {
        return charactersNetRepository.getEnemyByName(name)
    }

    override suspend fun getEnemiesList(): List<EnemyCardModel> {
        val enemies = mutableListOf<EnemyCardModel>()

        charactersNetRepository.getEnemies().onEach { name ->
            val enemy = charactersNetRepository.getEnemyByName(name)

            enemies.add(enemy.toCardModel(
                name,
                resources.getString(
                    R.string.character_enemy_card_image,
                    name
                ),
                enemy.elements?.map {
                    Element(
                        it, resources.getString(
                            R.string.character_element_icon,
                            it.lowercase()
                        )
                    )
                } ?: emptyList(),
                isEnemyCharacterInFavorite(name)
            ))
        }
        return enemies
    }

    override suspend fun getFavoritePlayableCharacters(): MutableList<HeroCardModel> {
        if (favoritePlayableCharacters.isEmpty()) {
            favoritePlayableCharacters.addAll(charactersDBRepository.getAllFavoritePlayableCharacters()
                .map { it.toCardModel(resources) })
        }
        return favoritePlayableCharacters
    }

    override suspend fun getFavoriteEnemiesCharacters(): MutableList<EnemyCardModel> {
        if (favoriteEnemyCharacters.isEmpty()) {
            favoriteEnemyCharacters.addAll(
                charactersDBRepository.getAllFavoriteEnemyCharacters()
                    .map { it.toCardModel(resources) })
        }
        return favoriteEnemyCharacters
    }

    override suspend fun addPlayableCharacterToFavorite(character: HeroCardModel) {
        favoritePlayableCharacters.add(character)
        charactersDBRepository.addPlayableCharacterToFavorite(character.toEntity())
    }

    override suspend fun addEnemyCharacterToFavorite(character: EnemyCardModel) {
        favoriteEnemyCharacters.add(character)
        charactersDBRepository.addEnemyCharacterToFavorite(character.toEntity())
    }

    override suspend fun removePlayableCharacterFromFavorite(characterId: String) {
        favoritePlayableCharacters.apply { remove(find { characterId == it.id }) }
        charactersDBRepository.deletePlayableCharacterById(characterId)
    }

    override suspend fun removeEnemyCharacterFromFavorite(characterId: String) {
        favoriteEnemyCharacters.apply { remove(find { characterId == it.id }) }
        charactersDBRepository.deleteEnemyCharacterById(characterId)
    }

    override suspend fun isPlayableCharacterInFavorite(characterId: String): Boolean {
        return favoritePlayableCharacters.any { it.id == characterId }
    }

    override suspend fun isEnemyCharacterInFavorite(characterId: String): Boolean {
        return favoriteEnemyCharacters.any { it.id == characterId }
    }
}