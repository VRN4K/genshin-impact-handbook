package com.gihandbook.my.domain.model

import androidx.palette.graphics.Palette
import com.gihandbook.my.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class CharacterUIModel(
    val name: String,
    val weaponType: WeaponType,
    val vision: Element,
    val description: String,
    val region: String,
    val constellationTitle: String,
    val constellationImageUrl: String,
    val rarity: Char,
    val imageUrl: String,
    val imageSideUrl: String,
    val imageUrlOnError: String,
    val passiveTalents: List<CharacterTalent>,
    val skillTalents: List<CharacterTalent>,
    val constellations: List<CharacterTalent>,
    val colorPalette: Palette
)

class CharacterTalent(
    val name: String,
    val unlock: String,
    val description: String,
    val talentImageUrlId: String? = null
)

data class Element(
    val name: String,
    val iconUrl: String,
    val description: String? = null
)

@Serializable
enum class WeaponType(val imageId: Int, val title: String) {
    @SerialName("Sword")
    SWORD(R.drawable.sword, "Sword"),

    @SerialName("Bow")
    BOW(R.drawable.bow, "Bow"),

    @SerialName("Catalyst")
    CATALYST(R.drawable.catalyst, "Catalyst"),

    @SerialName("Polearm")
    POLEARM(R.drawable.polearm, "Polearm"),

    @SerialName("Claymore")
    CLAYMORE(R.drawable.claymore, "Claymore")
}

enum class Vision {
    Anemo,
    Cryo,
    Dendro,
    Electro,
    Geo,
    Hydro,
    Pyro
}

abstract class CharacterCardModel(
    val id: String,
    open val name: String,
    val imageUrl: String,
    val imageUrlOnError: String,
    val region: String
)

class HeroCardModel(
    val weaponType: WeaponType,
    val element: Element,
    id: String,
    name: String,
    imageUrl: String,
    imageUrlOnError: String,
    region: String
) : CharacterCardModel(id, name, imageUrl, imageUrlOnError, region)