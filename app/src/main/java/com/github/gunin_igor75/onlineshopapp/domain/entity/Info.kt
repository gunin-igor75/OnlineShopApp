package com.github.gunin_igor75.onlineshopapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    val title: String,
    val value: String
): Parcelable
