package com.github.gunin_igor75.onlineshopapp.data.repository

import android.content.Context
import com.github.gunin_igor75.onlineshopapp.data.local.db.ItemDao
import com.github.gunin_igor75.onlineshopapp.data.local.db.UserDao
import com.github.gunin_igor75.onlineshopapp.data.local.model.ItemDbModel
import com.github.gunin_igor75.onlineshopapp.data.local.model.UserItemDbModel
import com.github.gunin_igor75.onlineshopapp.data.mapper.toItems
import com.github.gunin_igor75.onlineshopapp.domain.model.Item
import com.github.gunin_igor75.onlineshopapp.domain.repository.ItemRepository
import com.github.gunin_igor75.onlineshopapp.utils.UIContentDto
import com.github.gunin_igor75.onlineshopapp.utils.readJsonFromAssets
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val itemDao: ItemDao,
    context: Context
) : ItemRepository {

    private val _dataList: MutableList<Item>

    private val dataList
        get() = _dataList.toList()

    private val itemsChangeEvents = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    init {
        val jsonString = readJsonFromAssets(context, MOCK_JSON)
        val fakeItems = Gson().fromJson(jsonString, UIContentDto::class.java)
        _dataList = mutableListOf<Item>().apply { addAll(fakeItems.items.toItems()) }
    }

    override fun getItems(userId: Long): Flow<List<Item>> = flow {
        val favorites = itemDao.getItems(userId)
        (0.._dataList.size).forEach { index ->
            if (favorites.contains(_dataList[index].id)) {
                _dataList[index] = _dataList[index].copy(isFavorite = true)
            }
        }
        itemsChangeEvents.collect {
            emit(dataList)
        }
    }

    override fun getSortFeedbackDesc() {
        _dataList.sortedByDescending { it.feedback.rating }
        itemsChangeEvents.tryEmit(Unit)
    }

    override fun getSortPriceDesc() {
        _dataList.sortedByDescending { it.price.priceWithDiscount }
        itemsChangeEvents.tryEmit(Unit)
    }

    override fun getSortPriceAsc() {
        _dataList.sortedBy { it.price.priceWithDiscount }
        itemsChangeEvents.tryEmit(Unit)
    }

    override suspend fun saveFavoriteItem(userId: Long, item: Item) {
        val index = _dataList.indexOf(item)
        val numberItem = item.id
        existsUserById(userId)
        val itemId = itemDao.getItemByNumber(numberItem) ?: itemDao.insertItem(
            ItemDbModel(
                number = numberItem
            )
        )
        itemDao.insertUserItem(
            UserItemDbModel(
                userId = userId,
                itemId = itemId
            )
        )
        _dataList[index] = _dataList[index].copy(isFavorite = true)
        itemsChangeEvents.tryEmit(Unit)
    }

    override suspend fun deleteFavoriteItem(userId: Long, item: Item) {
        val index = _dataList.indexOf(item)
        val numberItem = item.id
        existsUserById(userId)
        val itemId = itemDao.getItemByNumber(numberItem)
            ?: throw IllegalStateException("Item number $numberItem does not exists")
        itemDao.deleteUserItem(UserItemDbModel(userId, itemId))
        _dataList[index] = _dataList[index].copy(isFavorite = false)
        itemsChangeEvents.tryEmit(Unit)
    }

    private suspend fun existsUserById(userId: Long) {
        val isExists = userDao.existsUserById(userId)
        if (!isExists) {
            throw IllegalStateException("User id $userId does not exists")
        }
    }

    companion object {
        private const val MOCK_JSON = "data.json"
    }
}