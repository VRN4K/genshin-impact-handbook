package com.gihandbook.my.ui.screens.weaponsscreen

import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import com.gihandbook.my.domain.StateLiveData
import com.gihandbook.my.domain.datacontracts.IWeaponInteractor
import com.gihandbook.my.domain.model.*
import com.gihandbook.my.ui.base.BaseViewModel
import com.gihandbook.my.ui.screens.charactersscreen.FilterItemsType
import dagger.hilt.android.lifecycle.HiltViewModel
import launchIO
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalMaterialApi::class)
class WeaponsScreenViewModel @Inject constructor(private val weaponInteractor: IWeaponInteractor) :
    BaseViewModel() {
    private var weapons = emptyList<WeaponCardModel>()
    var clickedWeapon = mutableStateOf<WeaponCardModel?>(null)

    val bottomState = ModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var isFilterShown = mutableStateOf(false)
    var isSearchShown = mutableStateOf(false)
    var selectedWeaponType = mutableStateOf<String?>(null)

    val weaponState = StateLiveData<List<WeaponCardModel>>()
    val searchQuery = mutableStateOf("")

    init {
        weaponState.postLoading()
        launchIO(handler) {
            weapons = weaponInteractor.getAllWeapons()
            weaponState.postComplete(weapons)
        }
    }

    fun onFilterChipClick(filterItemsType: FilterItemsType?, chipValue: String?) {
        when (filterItemsType) {
            FilterItemsType.WEAPON_TYPE -> selectedWeaponType.value = chipValue
            else -> {
                selectedWeaponType.value = null
            }
        }

        weaponState.postComplete(filterWeapon(chipValue?.let { WeaponType.valueOf(it.uppercase()) }))
    }

    private fun filterWeapon(weaponType: WeaponType? = null): List<WeaponCardModel> {
        weapons.apply {
            return when {
                weaponType != null -> this.filter { weapon -> weapon.type == weaponType }
                else -> this
            }
        }
    }

    fun onSystemSearchButtonClick(searchText: String) {
        searchQuery.value = searchText
        weapons.filter { it.name.contains(searchText, true) }.showWeapons()
    }

    private fun List<WeaponCardModel>.showWeapons() {
        weaponState.apply {
            if (isEmpty()) postNotFound() else postComplete(this@showWeapons)
        }
    }

    fun onClearButtonClick() {
        searchQuery.value = ""
        weaponState.postComplete(weapons)
    }

    fun onFilterClick() {
        isFilterShown.value = !isFilterShown.value
        if (isFilterShown.value) isSearchShown.value = false
    }

    fun onSearchButtonClick() {
        isSearchShown.value = !isSearchShown.value
        if (isSearchShown.value) isFilterShown.value = false
    }

    fun onCardClicked(weapon: WeaponCardModel) {
        clickedWeapon.value = weapon
    }

    fun onAddToFavoriteClick(isChecked: Boolean) {
        launchIO(handler) {
            if (isChecked) {
                weaponInteractor.addWeaponToFavorite(clickedWeapon.value!!)
            } else {
                weaponInteractor.removeWeaponFromFavorite(clickedWeapon.value!!.id)
            }
        }
    }
}