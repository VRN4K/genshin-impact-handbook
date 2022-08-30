package com.gihandbook.my.data.net.model

import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.EnemyCardModel
import kotlinx.serialization.Serializable

@Serializable
class Enemy(
    val name: String?,
    val region: String?,
    val elements: List<String>?
)

fun Enemy.toCardModel(imageUrl: String, elements: List<Element>): EnemyCardModel {
    return EnemyCardModel(
        elements,
        "",
        let { name } ?: "Unknown",
        imageUrl,
        let { region } ?: "Unknown"
    )
}