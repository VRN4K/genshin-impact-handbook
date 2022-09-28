package com.gihandbook.my.ui.screens.enemydetailsscreen

import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.model.EnemyUIModel
import com.gihandbook.my.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import launchIO
import javax.inject.Inject

@HiltViewModel
class EnemyDetailsScreenViewModel @Inject constructor(private val characterInteractor: ICharacterInteractor) :
    BaseViewModel() {
    private val mockName = "abyss-mage"
    val character = StateLiveData<EnemyUIModel>()

    init {
        character.postLoading()
        launchIO(handler) {
            character.postComplete(characterInteractor.getEnemyDetailInformation(mockName))
        }
    }

}