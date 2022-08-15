package com.gihandbook.my.ui.screens.charactersscreen

import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.model.CharacterCardModel
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.WeaponType
import com.gihandbook.my.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import launchIO
import withIO
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(private val characterInteractor: ICharacterInteractor) :
    BaseViewModel() {
    private var charactersFromServer = emptyList<CharacterCardModel>()
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
            charactersFromServer = characterInteractor.getHeroesList()
            characterState.postComplete(charactersFromServer)
            withIO { enemiesState.postComplete(characterInteractor.getEnemiesList()) }
        }
    }

    fun onWeaponFilterClick(weaponType: WeaponType? = null, element: String? = null) {
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

    fun onSearchButtonClick(searchText: String) {
        charactersFromServer.filter {
            it.name.contains(searchText, true) || it.region.contains(searchText, true)
        }.apply {
            if (size == 0) {
                characterState.postNotFound()
            } else {
                characterState.postComplete(this)
            }
        }
    }

    fun onClearButtonClick() {
        characterState.postComplete(charactersFromServer)
    }
}
