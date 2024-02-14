package com.github.gunin_igor75.onlineshopapp.presentation.main

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.github.gunin_igor75.onlineshopapp.presentation.main.basket.BasketContent
import com.github.gunin_igor75.onlineshopapp.presentation.main.home.HomeContent
import com.github.gunin_igor75.onlineshopapp.presentation.main.stock.StockContent

@Composable
fun MainContent(
    component: MainComponent
) {
    Children(stack = component.childStack) {
        when(val instance = it.instance){
            is MainComponent.Child.BasketChild -> {
                BasketContent(component = instance.basketComponent)
            }
            is MainComponent.Child.CatalogDetailsChild -> {

            }
            is MainComponent.Child.HomeChild -> {
                HomeContent(component = instance.homeComponent)
            }
            is MainComponent.Child.LoginFavoriteDetailsChild -> {

            }
            is MainComponent.Child.StockChild -> {
                StockContent(component = instance.stockComponent)
            }
        }
    }
}