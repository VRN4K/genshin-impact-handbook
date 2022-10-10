package com.gihandbook.my.domain.model

data class FilterDataModel(
    val weaponType: WeaponType? = null,
    val element: Vision? = null,
    val filteredElementList: List<String>? = null
)