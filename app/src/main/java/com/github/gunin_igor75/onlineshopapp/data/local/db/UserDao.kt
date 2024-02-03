package com.github.gunin_igor75.onlineshopapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.github.gunin_igor75.onlineshopapp.data.local.model.UserDbModel
import com.github.gunin_igor75.onlineshopapp.data.local.model.UserItemTuple

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(userDbModel: UserDbModel): Long

    @Query("SELECT * FROM users WHERE phone = :phone LIMIT 1")
    suspend fun getUserByPhone(phone: String): UserDbModel?

    @Query("SELECT EXISTS(SELECT * FROM users WHERE id = :userId LIMIT 1)")
    suspend fun existsUserById(userId: Long): Boolean

//    @Transaction
//    @Query("SELECT * FROM users WHERE users.id = :userId")
//    fun getUserAndItem(userId: Long): UserItemTuple
}