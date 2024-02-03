package com.github.gunin_igor75.onlineshopapp.domain.repository

import com.github.gunin_igor75.onlineshopapp.domain.model.SignData
import com.github.gunin_igor75.onlineshopapp.domain.model.User

interface UserRepository {

    suspend fun insertUser(signData: SignData):Long

    suspend fun getUserByPhone(phone: String):User?
}