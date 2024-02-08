package com.github.gunin_igor75.onlineshopapp.presentation.favorite

import com.github.gunin_igor75.onlineshopapp.domain.entity.Item

interface FavoriteComponent {
    fun onClickBack()
    fun onClickItem(item: Item)
    fun onChangeFavorite(item: Item)
}