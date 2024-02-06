package com.github.gunin_igor75.onlineshopapp.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.gunin_igor75.onlineshopapp.data.local.model.UserItemDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT EXISTS(SELECT * FROM users_items WHERE user_id = :userId AND item_id = :itemId)")
    fun observeIsFavorite(userId: Long, itemId: String): Flow<Boolean>
    @Query("SELECT item_id FROM users_items WHERE user_id = :userId")
    suspend fun getItemsIdIsFavorite(userId: Long): List<String>
    @Insert
    suspend fun insertUserItem(userItemDbModel: UserItemDbModel)
    @Delete
    suspend fun deleteUserItem(userItemDbModel: UserItemDbModel)
}
