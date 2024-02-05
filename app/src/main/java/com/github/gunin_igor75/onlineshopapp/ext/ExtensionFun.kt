package com.github.gunin_igor75.onlineshopapp.ext

fun String.isCheckUsername(): Boolean {
    val pattern = Regex("[а-яёА-ЯЁ]+")
    return pattern.matches(this).not()
}


fun String.isCheckPhone(): Boolean {
    val pattern = Regex("[0-9]+")
    return pattern.matches(this).not()
}