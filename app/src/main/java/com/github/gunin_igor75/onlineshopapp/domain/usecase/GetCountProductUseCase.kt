package com.github.gunin_igor75.onlineshopapp.domain.usecase

import com.github.gunin_igor75.onlineshopapp.domain.repository.ItemRepository
import javax.inject.Inject

class GetCountProductUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(userId: Long) = repository.getCountFavorite(userId)
}