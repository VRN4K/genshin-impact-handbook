package com.gihandbook.my.data.net.model

import android.content.res.Resources
import androidx.palette.graphics.Palette
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val name: String,
    val vision: String,
    val weapon: WeaponType,
    val constellation: String,
    val nation: String,
    val rarity: Char,
    val description: String,
    val skillTalents: List<SkillTalent>,
    val passiveTalents: List<PassiveTalent>,
    val constellations: List<Constellations>
)

@Serializable
data class SkillTalent(
    var name: String,
    var unlock: String,
    var description: String,
    var type: TalentsType? = TalentsType.RIGHT_CLICK
)

@Serializable
data class PassiveTalent(
    var name: String,
    var unlock: String,
    var description: String,
    var level: PassiveSkillsType? = PassiveSkillsType.UTILITY_PASSIVE
)

@Serializable
data class Constellations(
    var name: String,
    var unlock: String,
    var description: String,
    var level: ConstellationsLevels,
)

fun Character.toUI(id: String, resources: Resources, palette: Palette): CharacterUIModel {
    return CharacterUIModel(
        name = name,
        weaponType = weapon,
        vision = Element(
            vision,
            resources.getString(R.string.character_element_icon_image, vision.lowercase())
        ),
        description = description,
        region = nation,
        constellationTitle = constellation,
        constellationImageUrl = resources.getString(R.string.constellation_star_map, id),
        imageUrl = resources.getString(R.string.character_gacha_splash_image, id),
        imageSideUrl = resources.getString(R.string.character_side_image, id),
        imageUrlOnError = resources.getString(R.string.character_portrait_image, id),
        rarity = rarity,
        skillTalents = skillTalents.map {
            CharacterTalent(
                it.name,
                it.unlock,
                it.description,
                it.type?.let { skillType -> resources.getString(skillType.imageUrlId, id) }
            )
        },
        passiveTalents = passiveTalents.map {
            CharacterTalent(
                it.name,
                it.unlock,
                it.description,
                resources.getString(
                    it.level?.imageUrlId!!,
                    id
                )
            )
        },
        constellations = constellations.map {
            CharacterTalent(
                it.name,
                it.unlock,
                it.description,
                resources.getString(
                    it.level.imageUrlId,
                    id
                )
            )
        },
        colorPalette = palette
    )
}

@Serializable
enum class TalentsType(val imageUrlId: Int) {
    NORMAL_ATTACK(R.string.talent_normal_attack),
    ELEMENTAL_SKILL(R.string.talent_elemental_skill),
    ELEMENTAL_BURST(R.string.talent_elemental_burst),
    RIGHT_CLICK(R.string.talent_right_click)
}

@Serializable
enum class PassiveSkillsType(val imageUrlId: Int) {
    @SerialName("1")
    FIRST_ASCENSION_PASSIVE(R.string.talent_1st_ascension_passive),
    @SerialName("4")
    FOURTH_ASCENSION_PASSIVE(R.string.talent_4st_ascension_passive),
    UTILITY_PASSIVE(R.string.talent_utility_passive)
}

@Serializable
enum class ConstellationsLevels(val imageUrlId: Int) {
    @SerialName("1")
    FIRST_LEVEL(R.string.constellation_level_1),
    @SerialName("2")
    SECOND_LEVEL(R.string.constellation_level_2),
    @SerialName("3")
    THIRD_LEVEL(R.string.constellation_level_3),
    @SerialName("4")
    FOURTH_LEVEL(R.string.constellation_level_4),
    @SerialName("5")
    FIFTH_LEVEL(R.string.constellation_level_5),
    @SerialName("6")
    SIXTH_LEVEL(R.string.constellation_level_6)
}

fun Character.toCardModel(
    id: String,
    imageUrl: String,
    imageUrlOnError: String,
    elementImageUrl: String
): HeroCardModel {
    return HeroCardModel(
        weapon,
        Element(vision, elementImageUrl),
        id,
        name,
        imageUrl,
        imageUrlOnError,
        nation
    )
}