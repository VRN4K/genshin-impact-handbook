package com.gihandbook.my.domain.extensions

fun <T> List<T>.contains(list: List<T>): Boolean {
    var isContains = false

    list.onEach { item ->
        if (this.contains(item)) {
            isContains = true
        } else {
            isContains = false
            return false
        }
    }

    return isContains
}

fun String.convertNameToUrlName(): String {
    return this.lowercase().replace(" ", "-")
}

fun String.getMaxRarity(): Int {
    val rarityDelimiter = "/"
    if (this.contains(rarityDelimiter, false)) {
        return this.substringAfter(rarityDelimiter).toInt()
    }
    return this.toInt()
}
