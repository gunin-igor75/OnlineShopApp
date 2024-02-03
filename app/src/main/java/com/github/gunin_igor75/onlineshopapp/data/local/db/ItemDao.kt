package com.github.gunin_igor75.onlineshopapp.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.gunin_igor75.onlineshopapp.data.local.model.ItemDbModel
import com.github.gunin_igor75.onlineshopapp.data.local.model.UserItemDbModel

@Dao
interface ItemDao {
    @Query(
        "SELECT items.number " +
                "FROM items " +
                "INNER JOIN users_items " +
                "   ON item_id = users_items.item_id AND users_items.user_id = :userId"
    )
    suspend fun getItems(userId: Long): List<String>

    @Insert
    suspend fun insertItem(itemDbModel: ItemDbModel): Long

    @Insert
    suspend fun insertUserItem(userItemDbModel: UserItemDbModel)

    @Delete
    suspend fun deleteUserItem(userItemDbModel: UserItemDbModel)

    @Query("SELECT items.id FROM items WHERE items.number = :number")
    suspend fun getItemByNumber(number: String): Long?

    @Query("SELECT * FROM users_items WHERE user_id = :userId AND item_id = :itemId")
    suspend fun getUserItem(userId: Long, itemId: Long): UserItemDbModel?
}
