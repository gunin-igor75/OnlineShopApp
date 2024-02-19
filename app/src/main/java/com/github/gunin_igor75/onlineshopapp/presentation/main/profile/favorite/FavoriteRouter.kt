package com.github.gunin_igor75.onlineshopapp.presentation.main.profile.favorite

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed interface FavoriteScreenType {
    data object Products : FavoriteScreenType
    data object Brand : FavoriteScreenType
}

object FavoriteRouter {

    var currentScreen: MutableState<FavoriteScreenType> = mutableStateOf(FavoriteScreenType.Products)

    fun navigateTo(destination: FavoriteScreenType) {
        currentScreen.value = destination
    }
}