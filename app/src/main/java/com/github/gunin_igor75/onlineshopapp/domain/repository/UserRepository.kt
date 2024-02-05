package com.github.gunin_igor75.onlineshopapp.domain.repository

import com.github.gunin_igor75.onlineshopapp.domain.entity.SignData
import com.github.gunin_igor75.onlineshopapp.domain.entity.User

interface UserRepository {
    suspend fun insertUser(signData: SignData):Long
    suspend fun getUserByPhone(phone: String):User?
    suspend fun checkUser(signData: SignData): Boolean
}