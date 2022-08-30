package com.gihandbook.my.domain.model

class EnemyCardModel(
    val element: List<Element>,
    imageUrlOnError: String,
    name: String,
    imageUrl: String,
    region: String
) : CharacterCardModel(name, imageUrl, imageUrlOnError, region)
