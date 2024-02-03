package com.github.gunin_igor75.onlineshopapp.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserItemTuple(
    @Embedded val userDbModel: UserDbModel,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserItemDbModel::class,
            parentColumn = "user_id",
            entityColumn = "item_id"
        )
    )
    val items: List<ItemDbModel>
)