package com.github.gunin_igor75.onlineshopapp.domain.repository

import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import kotlinx.coroutines.flow.Flow


interface ItemRepository {
    fun getItems(userId: Long): Flow<List<Item>>
    fun getSortFeedbackDesc()
    fun getSortPriceDesc()
    fun observeIsFavorite(userId: Long, itemId: String): Flow<Boolean>
    fun getSortPriceAsc()
    fun getCountFavorite(userId: Long): Flow<String>
    suspend fun getChoseAll()

    suspend fun getChoseFace()

    suspend fun getChoseBody()

    suspend fun getChoseSuntan()

    suspend fun getChoseMask()
    suspend fun deleteFavoriteItem(userId: Long, itemId: String)
    suspend fun saveFavoriteItem(userId: Long, itemId: String)
    suspend fun deleteAllInfo()
}