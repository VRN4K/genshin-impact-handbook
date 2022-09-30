package com.gihandbook.my.domain.model

class EnemyCardModel(
    val element: List<Element>,
    id: String,
    imageUrlOnError: String,
    name: String,
    imageUrl: String,
    region: String
) : CharacterCardModel(id, name, imageUrl, imageUrlOnError, region)
