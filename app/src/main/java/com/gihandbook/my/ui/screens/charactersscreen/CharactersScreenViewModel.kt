package com.gihandbook.my.ui.screens.charactersscreen

import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.exstentions.contains
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.model.*
import com.gihandbook.my.ui.base.BaseViewModel
import com.gihandbook.my.ui.snippets.TabPagesCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import launchIO
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(private val characterInteractor: ICharacterInteractor) :
    BaseViewModel() {
    private var charactersFromServer = emptyList<CharacterCardModel>()
    private var enemiesFromServer = emptyList<CharacterCardModel>()
    val characterState = StateLiveData<List<CharacterCardModel>>()
    val enemiesState = StateLiveData<List<CharacterCardModel>>()

    init { getCharacters() }

    private fun getCharacters() {

        launchIO(CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
        }) {
            characterState.postLoading()
            enemiesState.postLoading()
            charactersFromServer = characterInteractor.getHeroesList()
            launchIO {
                enemiesFromServer = characterInteractor.getEnemiesList()
                enemiesState.postComplete(enemiesFromServer)
            }
            characterState.postComplete(charactersFromServer)
        }
    }

    fun onCharacterFilterChipClick(filterDataModel: FilterDataModel) {
        if (filterDataModel.selectedTab == TabPagesCharacters.CHARACTERS) {
            characterState.postComplete(
                charactersFromServer.filter(
                    filterDataModel.weaponType,
                    filterDataModel.element
                )
            )
        } else {
            enemiesState.postComplete(enemiesFromServer.filter(filterDataModel.filteredElementList!!))
        }
    }

    private fun List<CharacterCardModel>.filter(
        weaponType: WeaponType? = null,
        element: Vision? = null
    ): List<HeroCardModel> {
        this as List<HeroCardModel>
        return when {
            weaponType != null && element == null -> this.filter { character -> character.weaponType == weaponType }
            weaponType == null && element != null -> this.filter { character -> character.element.name == element.name }
            weaponType != null && element != null -> this.filter { character ->
                character.weaponType == weaponType && character.element.name == element.name
            }
            else -> this
        }
    }

    private fun List<CharacterCardModel>.filter(filteredElementList: List<String>): List<EnemyCardModel> {
        this as List<EnemyCardModel>
        return when {
            filteredElementList.isNotEmpty() -> this.filter { enemy ->
                enemy.element.map { it.name }.contains(filteredElementList)
            }
            else -> this
        }
    }

    fun onSearchButtonClick(searchText: String, selectedTab: TabPagesCharacters) {
        (if (selectedTab == TabPagesCharacters.CHARACTERS) charactersFromServer else enemiesFromServer).filter {
            it.name.contains(searchText, true) || it.region.contains(searchText, true)
        }.showCharacters(selectedTab)
    }

    private fun List<CharacterCardModel>.showCharacters(selectedTab: TabPagesCharacters) {
        (if (selectedTab == TabPagesCharacters.CHARACTERS) characterState else enemiesState).also {
            if (isEmpty()) it.postNotFound() else it.postComplete(this)
        }
    }

    fun onClearButtonClick(selectedTab: TabPagesCharacters) {
        if (selectedTab == TabPagesCharacters.CHARACTERS) {
            characterState.postComplete(charactersFromServer)
        } else {
            enemiesState.postComplete(enemiesFromServer)
        }
    }
}
