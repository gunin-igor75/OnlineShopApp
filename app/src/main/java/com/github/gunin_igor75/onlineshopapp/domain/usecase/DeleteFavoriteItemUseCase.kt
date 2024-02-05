package com.github.gunin_igor75.onlineshopapp.domain.usecase

import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.repository.ItemRepository
import javax.inject.Inject

class DeleteFavoriteItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(userId: Long, item: Item) =
        repository.deleteFavoriteItem(userId, item)
}