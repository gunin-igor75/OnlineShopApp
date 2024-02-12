package com.github.gunin_igor75.onlineshopapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Price(
    val price: Int,
    val discount: Int,
    val priceWithDiscount: Int,
    val unit: String
): Parcelable
