package com.gihandbook.my.domain.model

import com.gihandbook.my.data.net.model.Artifacts
import com.gihandbook.my.data.net.model.Drops

class EnemyUIModel(
    val name: String,
    val region: String?,
    val imageUrl: String,
    val description: String,
    val type: String,
    val elements: List<Element>?,
    val drops: List<Drops>,
    val artifacts: List<Artifacts>,
    val moraGained: String
)

class EnemyCardModel(
    val element: List<Element>,
    imageUrlOnError: String,
    name: String,
    imageUrl: String,
    region: String
) : CharacterCardModel(name, imageUrl, imageUrlOnError, region)
