package com.gihandbook.my.data.storage.entities

import android.content.res.Resources
import androidx.room.*
import com.gihandbook.my.R
import com.gihandbook.my.domain.extensions.convertNameToUrlName
import com.gihandbook.my.domain.model.Element
import com.gihandbook.my.domain.model.EnemyCardModel
import com.gihandbook.my.domain.model.HeroCardModel
import com.gihandbook.my.domain.model.WeaponType
import com.google.gson.Gson
import javax.inject.Inject

@Entity(tableName = "character_hero")
class HeroEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val element: String,
    val region: String,
    val weapon: String,
    val imageUrl: String,
    val imageUrlOnError: String,
)

fun HeroEntity.toCardModel(resources: Resources): HeroCardModel {
    return HeroCardModel(
        weaponType = WeaponType.valueOf(weapon.uppercase()),
        element = Element(
            element,
            resources.getString(
                R.string.character_element_icon_image,
                element.convertNameToUrlName()
            )
        ),
        id = id,
        name = name,
        imageUrl = imageUrl,
        imageUrlOnError = imageUrlOnError,
        region = region,
        isFavorite = true
    )
}

@Entity(tableName = "character_enemy")
class EnemyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    @TypeConverters(ElementConverter::class)
    val elements: List<String>?,
    val region: String,
    val imageUrl: String,
    val imageUrlOnError: String,
)

fun EnemyEntity.toCardModel(resources: Resources): EnemyCardModel {
    return EnemyCardModel(
        element = elements?.map {
            Element(
                it,
                resources.getString(
                    R.string.character_element_icon_image,
                    it.convertNameToUrlName()
                )
            )
        } ?: emptyList(),
        id = id,
        name = name,
        imageUrl = imageUrl,
        imageUrlOnError = imageUrlOnError,
        region = region,
        isFavorite = true
    )
}

@ProvidedTypeConverter
class ElementConverter {
    @Inject
    lateinit var gson: Gson

    @TypeConverter
    fun fromList(elements: List<String>): String {
        return gson.toJson(elements)
    }

    @TypeConverter
    fun toString(elements: String): List<String> {
        return gson.fromJson(elements, listOf<String>()::class.java)
    }
}


