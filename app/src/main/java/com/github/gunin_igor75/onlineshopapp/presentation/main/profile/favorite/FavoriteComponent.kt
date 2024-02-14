package com.github.gunin_igor75.onlineshopapp.presentation.main.profile.favorite

import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import kotlinx.coroutines.flow.StateFlow

interface FavoriteComponent {

    val model: StateFlow<FavoriteStore.State>
    fun onClickBack()
    fun onClickItem(item: Item)
    fun onChangeFavorite(item: Item)
}