package com.github.gunin_igor75.onlineshopapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.gunin_igor75.onlineshopapp.data.local.model.UserDbModel

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(userDbModel: UserDbModel): Long

    @Query("SELECT * FROM users WHERE phone = :phone LIMIT 1")
    suspend fun getUserByPhone(phone: String): UserDbModel?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): UserDbModel?
    @Query("DELETE FROM users")
    suspend fun deleteUsers()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE id = :userId LIMIT 1)")
    suspend fun existsUserById(userId: Long): Boolean

}