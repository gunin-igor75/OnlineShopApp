package com.github.gunin_igor75.onlineshopapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val name: String,
    val lastname: String,
    val phone: String
): Parcelable
