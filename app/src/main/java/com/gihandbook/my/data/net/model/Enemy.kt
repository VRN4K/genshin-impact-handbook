package com.gihandbook.my.data.net.model

import android.content.res.Resources
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.EnemyUIModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Enemy(
    val name: String,
    val region: String?,
    val description: String,
    val type: String,
    val elements: List<String>?,
    val drops: List<Drops>,
    val artifacts: List<Artifacts>,
    @SerialName("elemental-description")
    val elementalDescription: List<ElementalDescription>,
    @SerialName("mora-gained")
    val moraGained: Int
)

@Serializable
class Drops(
    val name: String,
    val rarity: Int,
    @SerialName("minimum-level")
    val minimum_level: Int
)

@Serializable
class Artifacts(
    val name: String,
    val rarity: String,
    val set: String
)

@Serializable
class ElementalDescription(
    val element: String,
    val description: String
)

fun Enemy.toCardModel(imageUrl: String, elements: List<Element>): EnemyCardModel {
    return EnemyCardModel(
        element = elements,
        imageUrlOnError = "",
        name = name,
        imageUrl = imageUrl,
        region = let { region } ?: "Unknown"
    )
}

fun Enemy.toUIModel(resources: Resources): EnemyUIModel {
    return EnemyUIModel(
        name = name,
        region = region,
        description = description,
        imageUrl = resources.getString(
            R.string.enemy_portrait_image,
            name.replace(" ", "-").lowercase()
        ),
        type = type,
        elements = elements?.map { element ->
            Element(
                element,
                resources.getString(
                    R.string.character_element_icon_image,
                    element.lowercase()
                ),
                elementalDescription.find { it.element == element }?.description ?: ""
            )
        },
        drops = drops,
        artifacts = artifacts,
        moraGained = this.moraGained.toString()
    )
}