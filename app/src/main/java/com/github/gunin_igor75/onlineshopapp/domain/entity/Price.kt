package com.github.gunin_igor75.onlineshopapp.domain.entity

data class Price(
    val price: Int,
    val discount: Int,
    val priceWithDiscount: Int,
    val unit: String
)
