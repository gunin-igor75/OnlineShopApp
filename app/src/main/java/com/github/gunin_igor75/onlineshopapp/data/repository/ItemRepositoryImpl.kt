package com.github.gunin_igor75.onlineshopapp.data.repository

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.data.local.db.ItemDao
import com.github.gunin_igor75.onlineshopapp.data.local.model.UserItemDbModel
import com.github.gunin_igor75.onlineshopapp.data.mapper.toItems
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.repository.ItemRepository
import com.github.gunin_igor75.onlineshopapp.domain.repository.UserRepository
import com.github.gunin_igor75.onlineshopapp.extentions.getIndex
import com.github.gunin_igor75.onlineshopapp.utils.UIContentDto
import com.github.gunin_igor75.onlineshopapp.utils.readJsonFromAssets
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDao,
    private val userRepo: UserRepository,
    private val context: Context,
) : ItemRepository {

    private var _items: MutableList<Item>

    private val _dataItems: MutableStateFlow<List<Item>> = MutableStateFlow(listOf())

    private val sortState = mutableStateOf(SortState.RATING)

    private val filterState = mutableStateOf(FilterState.ALL)

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        val jsonString = readJsonFromAssets(context, MOCK_JSON)
        val fakeItems = Gson().fromJson(jsonString, UIContentDto::class.java)
        _items = mutableListOf<Item>().apply {
            addAll(fakeItems.items.toItems())
            sortedByDescending { it.feedback.rating }
        }
        _dataItems.value = _items
    }

    override fun getItems(userId: Long): StateFlow<List<Item>> {
        scope.launch { setupFavorite() }
        return _dataItems.asStateFlow()
    }

    override fun getSortFeedbackDesc() {
        sortState.value = SortState.RATING
        val temp = getFilterList().sortedByDescending { it.feedback.rating }
        _dataItems.value = temp
    }

    override fun getSortPriceDesc() {
        sortState.value = SortState.PRICE_DESC
        val temp = getFilterList().sortedByDescending { it.price.priceWithDiscount }
        _dataItems.value = temp
    }

    override fun getSortPriceAsc() {
        sortState.value = SortState.PRICE_ASC
        val temp = getFilterList().sortedBy { it.price.priceWithDiscount }
        _dataItems.value = temp
    }

    override fun getChoseAll() {
        filterState.value = FilterState.ALL
        val temp = sortByState(_items)
        _dataItems.value = temp
    }

    override fun getChoseFace() {
        filterState.value = FilterState.FACE
        val temp = sortByState(getFilterList())
        _dataItems.value = temp
    }

    override fun getChoseBody() {
        filterState.value = FilterState.BODY
        val temp = sortByState(getFilterList())
        _dataItems.value = temp
    }

    override fun getChoseSuntan() {
        filterState.value = FilterState.SUNTAN
        val temp = sortByState(getFilterList())
        _dataItems.value = temp
    }

    override fun getChoseMask() {
        filterState.value = FilterState.MASK
        val temp = sortByState(getFilterList())
        _dataItems.value = temp
    }

    override suspend fun saveFavoriteItem(userId: Long, itemId: String) {
        val index = _items.getIndex(itemId)
            ?: throw IllegalArgumentException("Item with id $itemId not exists")
        val userItem = UserItemDbModel(userId, itemId)
        itemDao.insertUserItem(userItem)
        _items[index] = _items[index].copy(isFavorite = true)
        val temp = sortByState(getFilterList())
        _dataItems.value = temp
    }

    override suspend fun deleteFavoriteItem(userId: Long, itemId: String) {
        val index = _items.getIndex(itemId)
            ?: throw IllegalArgumentException("Item with id $itemId not exists")
        val userItem = UserItemDbModel(userId, itemId)
        itemDao.deleteUserItem(userItem)
        _items[index] = _items[index].copy(isFavorite = false)
        val temp = sortByState(getFilterList())
        _dataItems.value = temp
    }

    override fun observeIsFavorite(userId: Long, itemId: String): Flow<Boolean> =
        itemDao.observeIsFavorite(userId, itemId)


    override fun getFavorites(userId: Long): Flow<List<Item>> = flow {
        itemDao.getItemsIdIsFavorite(userId).collect { favorites ->
            val temp = _items.filter { favorites.contains(it.id) }
            emit(temp)
        }
    }


    override fun getCountFavorite(userId: Long): Flow<String> =
        itemDao.getCountFavorite(userId)
            .map { transformString(it) }


    private fun getFilterList(): List<Item> =
        when (val filter = filterState.value) {
            FilterState.ALL -> {
                _items.toList()
            }

            else -> {
                _items.filter { it.tags.contains(filter.param) }
            }
        }

    private fun sortByState(list: List<Item>): List<Item> =
        when (sortState.value) {
            SortState.RATING -> {
                list.sortedByDescending { it.feedback.rating }
            }

            SortState.PRICE_DESC -> {
                list.sortedByDescending { it.price.priceWithDiscount }
            }

            SortState.PRICE_ASC -> {
                list.sortedBy { it.price.priceWithDiscount }
            }
        }

    private suspend fun setupFavorite() {
        val currentUser = userRepo.currentUser.value
        val favorites = itemDao.getItemsIdIsFavorite(currentUser)
        favorites.collect {
            (0 until _items.size).forEach { index ->
                if (it.contains(_items[index].id)) {
                    _items[index] = _items[index].copy(isFavorite = true)
                }
            }
        }
    }

    private fun transformString(number: Int): String {
        return context.resources.getQuantityString(R.plurals.product_hint, number, number)
    }

    companion object {
        private const val MOCK_JSON = "data.json"
    }
}