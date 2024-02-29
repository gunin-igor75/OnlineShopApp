package com.github.gunin_igor75.onlineshopapp.domain.usecase

import com.github.gunin_igor75.onlineshopapp.domain.repository.ItemRepository
import javax.inject.Inject

class GetChoseAllUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke() = repository.getChoseAll()
}