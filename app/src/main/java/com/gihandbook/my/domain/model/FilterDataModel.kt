package com.gihandbook.my.domain.model

import com.gihandbook.my.ui.snippets.TabPagesCharacters

data class FilterDataModel(
    val selectedTab: TabPagesCharacters,
    val weaponType: WeaponType? = null,
    val element: Vision? = null,
    val filteredElementList: List<String>? = null
)