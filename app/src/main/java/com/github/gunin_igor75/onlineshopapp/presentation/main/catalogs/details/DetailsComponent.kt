package com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details

import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {

    val model: StateFlow<DetailsStore.State>
    fun onClickBack()
    fun onClickChangeFavorite()
}