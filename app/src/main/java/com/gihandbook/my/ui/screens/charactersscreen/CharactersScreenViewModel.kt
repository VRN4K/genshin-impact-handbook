package com.gihandbook.my.ui.screens.charactersscreen

import androidx.lifecycle.MutableLiveData
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.model.CharacterCardModel
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import launchIO
import withIO
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(private val characterInteractor: ICharacterInteractor) :
    BaseViewModel() {
    val characters = MutableLiveData<List<CharacterCardModel>>()
    val enemies = MutableLiveData<List<EnemyCardModel>>()

    init { getCharacters() }

    private fun getCharacters() {
        launchIO(CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
        }) {
            characters.postValue(characterInteractor.getHeroesList())
            withIO { enemies.postValue(characterInteractor.getEnemiesList()) }
        }
    }
}