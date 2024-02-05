package com.github.gunin_igor75.onlineshopapp.data.mapper

import com.github.gunin_igor75.onlineshopapp.data.local.model.UserDbModel
import com.github.gunin_igor75.onlineshopapp.domain.entity.SignData
import com.github.gunin_igor75.onlineshopapp.domain.entity.User


fun SignData.toUserDbModel(): UserDbModel {
    return UserDbModel(
        id = 0,
        name = this.name,
        lastname = this.lastname,
        phone = this.phone
    )
}

fun UserDbModel.toUser(): User {
    return User(id, name, lastname, phone)
}

