package com.github.gunin_igor75.onlineshopapp.domain.usecase

import com.github.gunin_igor75.onlineshopapp.domain.model.Item
import com.github.gunin_igor75.onlineshopapp.domain.repository.ItemRepository
import javax.inject.Inject

class SaveFavoriteItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(userId: Long, item: Item) =
        repository.saveFavoriteItem(userId, item)
}