package com.github.gunin_igor75.onlineshopapp.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items",
    indices = [
        Index("number", unique = true)
    ]
)
data class ItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val number: String
)
