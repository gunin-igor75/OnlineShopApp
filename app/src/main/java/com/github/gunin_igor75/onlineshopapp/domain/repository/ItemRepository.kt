package com.github.gunin_igor75.onlineshopapp.domain.repository

import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


interface ItemRepository {
    fun getItems(userId: Long): StateFlow<List<Item>>
    fun getSortFeedbackDesc()
    fun getSortPriceDesc()
    fun getSortPriceAsc()
    fun getChoseAll()
    fun getChoseFace()
    fun getChoseBody()
    fun getChoseSuntan()
    fun getChoseMask()
    suspend fun deleteFavoriteItem(userId: Long, itemId: String)
    suspend fun saveFavoriteItem(userId: Long, itemId: String)
    fun observeIsFavorite(userId: Long, itemId: String): Flow<Boolean>
    fun getFavorites(userId: Long): Flow<List<Item>>
    suspend fun deleteAllInfo()
    fun getCountFavorite(userId: Long): Flow<String>
}