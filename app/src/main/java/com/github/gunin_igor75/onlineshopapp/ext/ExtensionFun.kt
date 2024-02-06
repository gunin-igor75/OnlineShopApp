package com.github.gunin_igor75.onlineshopapp.ext

import com.github.gunin_igor75.onlineshopapp.domain.entity.Item

fun String.isCheckUsername(): Boolean {
    val pattern = Regex("[а-яёА-ЯЁ]+")
    return pattern.matches(this).not()
}


fun String.isCheckPhone(): Boolean {
    val pattern = Regex("[0-9]+")
    return pattern.matches(this).not()
}

fun List<Item>.getIndex(itemId: String): Int? {
    this.forEachIndexed { index, elem ->
        if (elem.id == itemId) {
            return index
        }
    }
    return null
}