package com.gihandbook.my.ui.screens.charactersscreen

import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.contains
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.model.CharacterCardModel
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.WeaponType
import com.gihandbook.my.ui.base.BaseViewModel
import com.gihandbook.my.ui.snippets.TabPagesCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import launchIO
import withIO
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(private val characterInteractor: ICharacterInteractor) :
    BaseViewModel() {
    private var charactersFromServer = emptyList<CharacterCardModel>()
    private var enemiesFromServer = emptyList<EnemyCardModel>()
    val characterState = StateLiveData<List<CharacterCardModel>>()
    val enemiesState = StateLiveData<List<EnemyCardModel>>()

    init {
        getCharacters()
    }

    private fun getCharacters() {

        launchIO(CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
        }) {
            characterState.postLoading()
            enemiesState.postLoading()
            charactersFromServer = characterInteractor.getHeroesList()
            withIO {
                enemiesFromServer = characterInteractor.getEnemiesList()
                enemiesState.postComplete(enemiesFromServer)
            }
            characterState.postComplete(charactersFromServer)
        }
    }

    fun onCharacterFilterChipClick(weaponType: WeaponType? = null, element: String? = null) {
        when {
            weaponType != null -> characterState.postComplete(with(charactersFromServer) {
                if (element == null) {
                    filter { character -> character.weaponType == weaponType }
                } else {
                    filter { character ->
                        character.weaponType == weaponType && character.element.name == element
                    }
                }
            })
            element != null -> characterState.postComplete(with(charactersFromServer) {
                filter { character -> character.element.name == element }
            })
            else -> characterState.postComplete(charactersFromServer)
        }
    }

    fun onEnemyFilterChipClick(filteredElementList: List<String>) {
        when {
            filteredElementList.isNotEmpty() -> enemiesState.postComplete(
                with(enemiesFromServer) {
                    filter { enemy ->
                        enemy.element?.map { it.name }?.contains(filteredElementList)
                            ?: enemy.element.isNullOrEmpty()
                    }
                })
            else -> enemiesState.postComplete(enemiesFromServer)
        }
    }


    fun onSearchButtonClick(searchText: String, selectedTab: TabPagesCharacters) {
        if (selectedTab == TabPagesCharacters.CHARACTERS) {
            charactersFromServer.filter {
                it.name.contains(searchText, true) || it.region.contains(searchText, true)
            }.apply {
                if (size == 0) {
                    characterState.postNotFound()
                } else {
                    characterState.postComplete(this)
                }
            }
        } else {
            enemiesFromServer.filter {
                it.name.contains(searchText, true) || it.region.contains(searchText, true)
            }.apply {
                if (size == 0) {
                    enemiesState.postNotFound()
                } else {
                    enemiesState.postComplete(this)
                }
            }
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
