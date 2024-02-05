package com.github.gunin_igor75.onlineshopapp.domain.entity

data class Item(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: Price,
    val feedback: Feedback,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<Info>,
    val ingredients: String,
    val isFavorite: Boolean = false
)
