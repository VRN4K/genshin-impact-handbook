package com.gihandbook.my.ui.screens.characterdetailscreen

import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.model.CharacterUIModel
import com.gihandbook.my.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import launchIO
import javax.inject.Inject

@HiltViewModel
class CharacterDetailScreenViewModel @Inject constructor(private val characterInteractor: ICharacterInteractor) :
    BaseViewModel() {

    fun setInitSettings(name: String) {
        TODO()
    }

    private val mockName = "Razor"
    val character = StateLiveData<CharacterUIModel>()

    init {
        character.postLoading()
        launchIO(handler) {
            character.postComplete(characterInteractor.getHeroDetailInformation(mockName.lowercase()))
        }
    }
}