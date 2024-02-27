package com.github.gunin_igor75.onlineshopapp.presentation.main.basket

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.presentation.component.TopBarApp

@Composable
fun BasketContent(
    paddingValues: PaddingValues,
    component: BasketComponent
) {
    TopBarApp(titleResId = R.string.title_basket)
}