package com.github.gunin_igor75.onlineshopapp.domain.repository

import com.github.gunin_igor75.onlineshopapp.domain.entity.SignData
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    suspend fun insertUser(signData: SignData):Long
    suspend fun getUserByPhone(phone: String):User?
    suspend fun getUserById(userId: Long): User?
    val currentUser: StateFlow<Long>
    suspend fun deleteUser(userId: Long)
}