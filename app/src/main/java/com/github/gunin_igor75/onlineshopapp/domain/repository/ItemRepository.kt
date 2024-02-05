package com.github.gunin_igor75.onlineshopapp.domain.repository

import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import kotlinx.coroutines.flow.Flow


interface ItemRepository {

    fun getItems(userId: Long): Flow<List<Item>>

    fun getSortFeedbackDesc()

    fun getSortPriceDesc()

    fun getSortPriceAsc()
    fun getChoseFace()
    fun getChoseBody()
    fun getChoseSuntan()
    fun getChoseMask()
    suspend fun saveFavoriteItem(userId: Long, item: Item)

    suspend fun deleteFavoriteItem(userId: Long, item: Item)

}