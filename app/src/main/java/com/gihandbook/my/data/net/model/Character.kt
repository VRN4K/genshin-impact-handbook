package com.gihandbook.my.data.net.model

import android.content.res.Resources
import androidx.compose.ui.res.stringResource
import com.gihandbook.my.R
import com.gihandbook.my.domain.model.*
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val name: String,
    val vision: String,
    val weapon: WeaponType,
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
    var type: TalentsType
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

fun Character.toUI(resources: Resources): CharacterUIModel {
    return CharacterUIModel(
        name = this.name,
        weaponType = this.weapon,
        vision = Element(
            this.vision,
            "https://api.genshin.dev/elements/${this.vision.lowercase()}/icon"
        ),
        description = this.description,
        imageUrl = "https://api.genshin.dev/characters/${name.lowercase()}/gacha-splash",
        imageSideUrl = "https://api.genshin.dev/characters/${name.lowercase()}/icon-side",
        imageUrlOnError = "https://api.genshin.dev/characters/${name.lowercase()}/portrait",
        rarity = this.rarity,
        skillTalents = this.skillTalents.map {
            CharacterTalent(
                it.name,
                it.unlock,
                it.description,
                resources.getString(it.type.imageUrlId, this.name.lowercase())
            )
        },
        passiveTalents = this.passiveTalents.map {
            CharacterTalent(
                it.name,
                it.unlock,
                it.description,
                resources.getString(
                    getPassiveTalentByLevel(it.level)?.imageUrlId!!,
                    this.name.lowercase()
                )
            )
        }
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

private fun getPassiveTalentByLevel(level: Int?): PassiveSkillsType? {
    return when (level) {
        PassiveSkillsType.FIRST_ASCENSION_PASSIVE.level -> PassiveSkillsType.FIRST_ASCENSION_PASSIVE
        PassiveSkillsType.FOURTH_ASCENSION_PASSIVE.level -> PassiveSkillsType.FOURTH_ASCENSION_PASSIVE
        PassiveSkillsType.UTILITY_PASSIVE.level -> PassiveSkillsType.UTILITY_PASSIVE
        else -> null
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