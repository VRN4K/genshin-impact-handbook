package com.gihandbook.my.domain

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

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

@SuppressLint("SimpleDateFormat")
fun String.fromStringToDate(patternFrom: String, patternTo: String): Date {
    val dateFormat = SimpleDateFormat(patternFrom)
    val fromDate = dateFormat.parse(this)
    dateFormat.applyPattern(patternTo)

    return requireNotNull(
        dateFormat.parse(dateFormat.format(requireNotNull(fromDate)))
    )
}
