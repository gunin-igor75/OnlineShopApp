package com.github.gunin_igor75.onlineshopapp.presentation.main

import com.github.gunin_igor75.onlineshopapp.R

sealed class Screen(val titleResId: Int) {
    data object Home : Screen(R.string.title_home)
    data object Catalog : Screen(R.string.title_catalog)
    data object Basket : Screen(R.string.title_basket)
    data object Stock : Screen(R.string.title_stock)
    data object Login : Screen(R.string.title_profile)
}