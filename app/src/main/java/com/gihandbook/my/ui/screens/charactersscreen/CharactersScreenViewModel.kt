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

    private var lastFilteredWeaponType: WeaponType? = null
    private var lastFilteredVision: String? = null


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
            weaponType != null
            -> characterState.postComplete(charactersFromServer.filter { character ->
                if (lastFilteredVision == null) character.weaponType == weaponType else
                    character.weaponType == weaponType && character.element.name == lastFilteredVision
            }).also { lastFilteredWeaponType = weaponType }
            element != null -> characterState.postComplete(charactersFromServer.filter { character ->
                if (lastFilteredWeaponType == null) character.element.name == element else
                    character.weaponType == lastFilteredWeaponType && character.element.name == element
            }).also { lastFilteredVision = element }
            lastFilteredVision != null -> characterState.postComplete(
                charactersFromServer.filter { character -> character.element.name == lastFilteredVision })
            lastFilteredWeaponType != null -> characterState.postComplete(
                charactersFromServer.filter { character -> character.weaponType == lastFilteredWeaponType })
            else -> characterState.postComplete(charactersFromServer).also {
                lastFilteredVision = null
                lastFilteredWeaponType = null
            }
        }
    }

    fun onSearchButtonClick(searchText: String) {
        charactersFromServer.filter { it.name == searchText || it.region == searchText }.apply {
            if (size == 0) characterState.postNotFound() else characterState.postComplete(this)
        }
    }

    fun onClearButtonClick() {
        characterState.postComplete(charactersFromServer)
    }
}
