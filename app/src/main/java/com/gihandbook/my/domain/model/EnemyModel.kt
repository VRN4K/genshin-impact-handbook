package com.gihandbook.my.domain.model

class EnemyUIModel(
    val name: String,
    val region: String?,
    val imageUrl: String,
    val description: String?,
    val type: String,
    val elements: List<Element>?,
    val drops: List<DropUI>?,
    val artifacts: List<ArtifactUI>?,
    val moraGained: String
)

class DropUI(
    val name: String,
    val rarity: Int,
    val minimum_level: Int,
    val imageUrl: String,
    val imageUrlOnError: String
)

class ArtifactUI(
    val name: String,
    val rarity: Int,
    val set: String,
    val imageUrl: String
)

class EnemyCardModel(
    val element: List<Element>,
    id: String,
    imageUrlOnError: String,
    name: String,
    imageUrl: String,
    region: String
) : CharacterCardModel(id, name, imageUrl, imageUrlOnError, region)
