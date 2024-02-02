package com.github.gunin_igor75.onlineshopapp.domain.model

import java.util.UUID

data class Item(
    val id: UUID,
    val title: String,
    val subtitle: String,
    val price: Price,
    val feedback: Feedback,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<Info>,
    val ingredients: String,
    val isFavorite: Boolean
)
