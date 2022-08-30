package com.gihandbook.my.domain.exstentions

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