package com.github.gunin_igor75.onlineshopapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "users_items",
    primaryKeys = ["user_id", "item_id"],
    indices = [Index("item_id")],
    foreignKeys = [
        ForeignKey(
            entity = UserDbModel::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserItemDbModel(
    @ColumnInfo("user_id") val userId: Long,
    @ColumnInfo("item_id") val itemId: String
)
