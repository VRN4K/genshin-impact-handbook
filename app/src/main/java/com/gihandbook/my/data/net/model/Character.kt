package com.gihandbook.my.data.net.model

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import com.bumptech.glide.Glide
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.*
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
    var type: TalentsType? = null
)

@Serializable
data class PassiveTalent(
    var name: String,
    var unlock: String,
    var description: String,
    var level: Int? = null
)

@Serializable
data class Constellations(
    var name: String,
    var unlock: String,
    var description: String,
    var level: Int,
)

fun Character.toUI(context: Context, bitmap: Bitmap? = null): CharacterUIModel {
    return CharacterUIModel(
        name = this.name,
        weaponType = this.weapon,
        vision = Element(
            this.vision,
            context.resources.getString(
                R.string.character_element_icon_image,
                this.vision.lowercase()
            )
        ),
        description = this.description,
        region = this.nation,
        constellationTitle = this.constellation,
        constellationImageUrl = context.resources.getString(
            R.string.constellation_star_map,
            this.name.lowercase()
        ),
        imageUrl = context.resources.getString(
            R.string.character_gacha_splash_image,
            this.name.lowercase()
        ),
        imageSideUrl = context.resources.getString(
            R.string.character_side_image,
            this.name.lowercase()
        ),
        imageUrlOnError = context.resources.getString(
            R.string.character_portrait_image,
            this.name.lowercase()
        ),
        rarity = this.rarity,
        skillTalents = this.skillTalents.map {
            CharacterTalent(
                it.name,
                it.unlock,
                it.description,
                context.resources.getString(it.type!!.imageUrlId, this.name.lowercase())
            )
        },
        passiveTalents = this.passiveTalents.map {
            CharacterTalent(
                it.name,
                it.unlock,
                it.description,
                context.resources.getString(
                    getPassiveTalentByLevel(it.level)?.imageUrlId!!,
                    this.name.lowercase()
                )
            )
        },
        constellations = this.constellations.map {
            CharacterTalent(
                it.name,
                it.unlock,
                it.description,
                context.resources.getString(
                    getConstellationsUrlByLevel(it.level),
                    this.name.lowercase()
                )
            )
        },
        color = bitmap
    )
}

@Serializable
enum class TalentsType(val imageUrlId: Int) {
    NORMAL_ATTACK(R.string.talent_normal_attack),
    ELEMENTAL_SKILL(R.string.talent_elemental_skill),
    ELEMENTAL_BURST(R.string.talent_elemental_burst),
}

enum class PassiveSkillsType(val level: Int?, val imageUrlId: Int) {
    FIRST_ASCENSION_PASSIVE(1, R.string.talent_1st_ascension_passive),
    FOURTH_ASCENSION_PASSIVE(4, R.string.talent_4st_ascension_passive),
    UTILITY_PASSIVE(null, R.string.talent_utility_passive)
}

enum class ConstellationsLevels(val level: Int, val imageUrlId: Int) {
    FIRST_LEVEL(1, R.string.constellation_level_1),
    SECOND_LEVEL(2, R.string.constellation_level_2),
    THIRD_LEVEL(3, R.string.constellation_level_3),
    FOURTH_LEVEL(4, R.string.constellation_level_4),
    FIFTH_LEVEL(5, R.string.constellation_level_5),
    SIXTH_LEVEL(6, R.string.constellation_level_6)
}

private fun getPassiveTalentByLevel(level: Int?): PassiveSkillsType? {
    return when (level) {
        PassiveSkillsType.FIRST_ASCENSION_PASSIVE.level -> PassiveSkillsType.FIRST_ASCENSION_PASSIVE
        PassiveSkillsType.FOURTH_ASCENSION_PASSIVE.level -> PassiveSkillsType.FOURTH_ASCENSION_PASSIVE
        PassiveSkillsType.UTILITY_PASSIVE.level -> PassiveSkillsType.UTILITY_PASSIVE
        else -> null
    }
}

private fun getConstellationsUrlByLevel(level: Int): Int {
    return when (level) {
        ConstellationsLevels.FIRST_LEVEL.level -> ConstellationsLevels.FIRST_LEVEL.imageUrlId
        ConstellationsLevels.SECOND_LEVEL.level -> ConstellationsLevels.SECOND_LEVEL.imageUrlId
        ConstellationsLevels.THIRD_LEVEL.level -> ConstellationsLevels.THIRD_LEVEL.imageUrlId
        ConstellationsLevels.FOURTH_LEVEL.level -> ConstellationsLevels.FOURTH_LEVEL.imageUrlId
        ConstellationsLevels.FIFTH_LEVEL.level -> ConstellationsLevels.FIFTH_LEVEL.imageUrlId
        ConstellationsLevels.SIXTH_LEVEL.level -> ConstellationsLevels.SIXTH_LEVEL.imageUrlId
        else -> {
            0
        }
    }
}

fun Character.toCardModel(
    imageUrl: String,
    imageUrlOnError: String,
    elementImageUrl: String
): HeroCardModel {
    return HeroCardModel(
        weapon,
        Element(vision, elementImageUrl),
        name,
        imageUrl,
        imageUrlOnError,
        nation
    )
}