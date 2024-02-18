package com.github.gunin_igor75.onlineshopapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val name: String,
    val lastname: String,
    val phone: String
): Parcelable{

    val fullName: String
        get() = "$name $lastname"
    companion object {
        val USER_DEFAULT = User(
            id = 0L,
            name = "Мария",
            lastname = "Иванова",
            phone = "9938774402"
        )
    }
}
