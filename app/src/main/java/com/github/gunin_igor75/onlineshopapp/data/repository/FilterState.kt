package com.github.gunin_igor75.onlineshopapp.data.repository

enum class FilterState(
    val param: String
) {
    ALL("all"),
    FACE("face"),
    BODY("body"),
    SUNTAN("suntan"),
    MASK("mask");
}