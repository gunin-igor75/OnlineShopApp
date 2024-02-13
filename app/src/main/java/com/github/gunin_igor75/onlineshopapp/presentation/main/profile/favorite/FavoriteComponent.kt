package com.github.gunin_igor75.onlineshopapp.presentation.main.profile.favorite

import com.github.gunin_igor75.onlineshopapp.domain.entity.Item

interface FavoriteComponent {
    fun onClickBack()
    fun onClickItem(item: Item)
    fun onChangeFavorite(item: Item)
}