package com.github.gunin_igor75.onlineshopapp.data.repository

import com.github.gunin_igor75.onlineshopapp.data.local.db.UserDao
import com.github.gunin_igor75.onlineshopapp.data.mapper.toUser
import com.github.gunin_igor75.onlineshopapp.data.mapper.toUserDbModel
import com.github.gunin_igor75.onlineshopapp.domain.entity.SignData
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(signData: SignData): Long {
        return userDao.insertUser(signData.toUserDbModel())
    }

    override suspend fun getUserByPhone(phone: String): User? {
        return userDao.getUserByPhone(phone)?.toUser()
    }

    override suspend fun checkUser(signData: SignData): Boolean {
        val user = userDao.getUserByPhone(signData.phone)
        if (user != null && (user.name == signData.name && user.lastname == signData.lastname)) {
            return false
        }
        return true
    }
}