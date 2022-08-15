package com.gihandbook.my.domain.model

import com.gihandbook.my.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Element(
    val name: String,
    val iconUrl: String
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

data class CharacterCardModel(
    val name: String,
    val imageUrl: String,
    val imageUrlOnError: String,
    val region: String,
    val weaponType: WeaponType,
    val element: Element
)