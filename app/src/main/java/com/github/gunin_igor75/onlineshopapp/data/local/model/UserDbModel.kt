package com.github.gunin_igor75.onlineshopapp.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "users",
    indices = [
        Index("phone", unique = true)
    ]
)
data class UserDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val phone: String,
    val name: String,
    val lastname: String
)
