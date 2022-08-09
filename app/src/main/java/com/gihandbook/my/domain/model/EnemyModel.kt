package com.gihandbook.my.domain.model

data class EnemyCardModel(
    val name: String,
    val imageUrl: String,
    val region: String,
    val element: List<Element>?
)
