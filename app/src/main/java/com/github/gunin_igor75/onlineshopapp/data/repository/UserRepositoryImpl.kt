package com.github.gunin_igor75.onlineshopapp.data.repository

import com.github.gunin_igor75.onlineshopapp.data.local.db.UserDao
import com.github.gunin_igor75.onlineshopapp.data.mapper.toUser
import com.github.gunin_igor75.onlineshopapp.data.mapper.toUserDbModel
import com.github.gunin_igor75.onlineshopapp.domain.entity.SignData
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    private val _currentUser: MutableStateFlow<Long> = MutableStateFlow(-1)

    override val currentUser = _currentUser.asStateFlow()

    override fun checkPhoneLength(phone: String):Boolean = phone.length == 10

    override suspend fun insertUser(signData: SignData): Long {
        val uerId = userDao.insertUser(signData.toUserDbModel())
        _currentUser.value = uerId
        return uerId
    }

    override suspend fun getUserByPhone(phone: String): User? {
        val user = userDao.getUserByPhone(phone)?.toUser()
        user?.let { _currentUser.value = it.id }
        return user
    }
    override suspend fun getUserById(userId: Long): User? {
        return userDao.getUserById(userId)?.toUser()
    }

    override suspend fun deleteUser(userId: Long) {
        userDao.deleteUser(userId)
    }
}