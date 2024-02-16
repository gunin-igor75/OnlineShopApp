package com.github.gunin_igor75.onlineshopapp.presentation.main

import com.github.gunin_igor75.onlineshopapp.R

sealed class Screen(val titleResId: Int) {
    data object Home : Screen(R.string.home)
    data object Catalog : Screen(R.string.catalog)
    data object Basket : Screen(R.string.basket)
    data object Stock : Screen(R.string.stock)
    data object Login : Screen(R.string.profile)
}