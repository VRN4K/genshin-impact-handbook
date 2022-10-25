package com.gihandbook.my.ui.screens.charactersscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import asyncIO
import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.extensions.contains
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.model.*
import com.gihandbook.my.ui.base.BaseViewModel
import com.gihandbook.my.ui.snippets.TabPagesCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import launchIO
import launchUI
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(
    private val characterInteractor: ICharacterInteractor
) : BaseViewModel() {

    private var charactersFromServer = emptyList<HeroCardModel>()
    private var enemiesFromServer = emptyList<EnemyCardModel>()

    var selectedTab = mutableStateOf(TabPagesCharacters.CHARACTERS)
    var isFilterShown = mutableStateOf(false)
    var isSearchShown = mutableStateOf(false)

    val selectedEnemiesVision = mutableStateListOf<String>()
    var selectedCharactersWeaponType = mutableStateOf<String?>(null)
    var selectedCharactersVision = mutableStateOf<String?>(null)

    val searchQuery = mutableStateOf("")

    val characterState = StateLiveData<List<CharacterCardModel>>()
    val enemiesState = StateLiveData<List<CharacterCardModel>>()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        launchUI(handler) {
            characterState.postLoading()
            enemiesState.postLoading()

            val charactersFromServerJob = asyncIO { characterInteractor.getHeroesList() }
            val enemiesFromServerJob = asyncIO { characterInteractor.getEnemiesList() }

            charactersFromServer = charactersFromServerJob.await()
            enemiesFromServer = enemiesFromServerJob.await()

            characterState.postComplete(charactersFromServer)
            enemiesState.postComplete(enemiesFromServer)
        }
    }

    fun onCharacterFilterChipClick(filterItemsType: FilterItemsType?, chipValue: String?) {
        when (filterItemsType) {
            FilterItemsType.WEAPON_TYPE -> selectedCharactersWeaponType.value = chipValue
            FilterItemsType.VISION -> selectedCharactersVision.value = chipValue
            else -> {
                selectedCharactersWeaponType.value = null
                selectedCharactersVision.value = null
            }
        }

        characterState.postComplete(
            filterHeroesByWeaponAndElement(
                selectedCharactersWeaponType.value?.let { WeaponType.valueOf(it.uppercase()) },
                selectedCharactersVision.value?.let { Vision.valueOf(it) }
            )
        )
    }

    fun onEnemyFilterChipClick(filterItemsType: FilterItemsType?, chipValue: String?) {
        when {
            filterItemsType == FilterItemsType.VISION && !selectedEnemiesVision.contains(chipValue) ->
                selectedEnemiesVision.add(chipValue!!)
            filterItemsType == FilterItemsType.VISION && selectedEnemiesVision.contains(chipValue) ->
                selectedEnemiesVision.remove(chipValue!!)
            else -> onClearButtonClick()
        }

        enemiesState.postComplete(filterEnemiesByElementsList(this.selectedEnemiesVision))
    }

    private fun filterHeroesByWeaponAndElement(
        weaponType: WeaponType? = null,
        element: Vision? = null
    ): List<HeroCardModel> {
        charactersFromServer.apply {
            return when {
                weaponType != null && element == null -> filter { character -> character.weaponType == weaponType }
                weaponType == null && element != null -> filter { character -> character.element.name == element.name }
                weaponType != null && element != null -> filter { character ->
                    character.weaponType == weaponType && character.element.name == element.name
                }
                else -> this
            }
        }
    }

    private fun filterEnemiesByElementsList(filteredElementList: List<String>): List<EnemyCardModel> {
        enemiesFromServer.apply {
            return when {
                filteredElementList.isNotEmpty() -> filter { enemy ->
                    enemy.element.map { it.name }.contains(filteredElementList)
                }
                else -> this
            }
        }
    }

    fun onSystemSearchButtonClick(searchText: String) {
        searchQuery.value = searchText
        (if (this.selectedTab.value == TabPagesCharacters.CHARACTERS) charactersFromServer else enemiesFromServer).filter {
            it.name.contains(searchText, true) || it.region.contains(searchText, true)
        }.showCharacters()
    }

    private fun List<CharacterCardModel>.showCharacters() {
        (if (selectedTab.value == TabPagesCharacters.CHARACTERS) characterState else enemiesState).also {
            if (isEmpty()) it.postNotFound() else it.postComplete(this)
        }
    }

    fun onClearButtonClick() {
        if (selectedTab.value == TabPagesCharacters.CHARACTERS) {
            searchQuery.value = ""
            characterState.postComplete(charactersFromServer)
        } else {
            searchQuery.value = ""
            selectedEnemiesVision.clear()
            enemiesState.postComplete(enemiesFromServer)
        }
    }

    fun onTabClick(selectedTab: TabPagesCharacters) {
        this.selectedTab.value = selectedTab
        isFilterShown.value = false
        isSearchShown.value = false
    }

    fun onFilterClick() {
        isFilterShown.value = !isFilterShown.value
        if (isFilterShown.value) isSearchShown.value = false
    }

    fun onSearchButtonClick() {
        isSearchShown.value = !isSearchShown.value
        if (isSearchShown.value) isFilterShown.value = false
    }

    fun onAddToFavoriteClick(character: CharacterCardModel, isChecked: Boolean) {
        character.isFavorite = isChecked

        launchIO {
            if (isChecked) {
                addCharacterToFavorite(character)
            } else {
                removeCharacterFromFavorite(character)
            }
        }
    }

    private suspend fun addCharacterToFavorite(character: CharacterCardModel) {
        if (selectedTab.value == TabPagesCharacters.CHARACTERS) {
            characterInteractor.addPlayableCharacterToFavorite(character as HeroCardModel)
        } else {
            characterInteractor.addEnemyCharacterToFavorite(character as EnemyCardModel)
        }
    }

    private suspend fun removeCharacterFromFavorite(character: CharacterCardModel) {
        if (selectedTab.value == TabPagesCharacters.CHARACTERS) {
            characterInteractor.removePlayableCharacterFromFavorite(character.id)
        } else {
            characterInteractor.removeEnemyCharacterFromFavorite(character.id)
        }
    }
}

enum class FilterItemsType {
    WEAPON_TYPE, VISION
}