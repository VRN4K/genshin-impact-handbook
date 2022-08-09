package com.gihandbook.my.data.net.model

import com.gihandbook.my.domain.model.CharacterCardModel
import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.WeaponType
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val name: String,
    val vision: String,
    val weapon: WeaponType,
    val nation: String,
    val skillTalents: List<SkillTalents>,
    val passiveTalents: List<PassiveTalents>,
    val constellations: List<Constellations>
)

@Serializable
data class SkillTalents(
    var name: String,
    var unlock: String,
    var description: String,
    var type: String?
)

@Serializable
data class PassiveTalents(
    var name: String,
    var unlock: String,
    var description: String,
    var level: Int?
)

@Serializable
data class Constellations(
    var name: String,
    var unlock: String,
    var description: String,
    var level: Int?
)

fun Character.toCardModel(
    imageUrl: String,
    imageUrlOnError: String,
    elementImageUrl: String
): CharacterCardModel {
    return CharacterCardModel(
        this.name,
        imageUrl,
        imageUrlOnError,
        this.nation,
        this.weapon,
        Element(this.vision, elementImageUrl)
    )
}