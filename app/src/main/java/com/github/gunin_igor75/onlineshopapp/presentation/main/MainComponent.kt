package com.github.gunin_igor75.onlineshopapp.presentation.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.CatalogDetailsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.login_favorite_details.LoginFavoriteDetailsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.basket.BasketComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.home.HomeComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.stock.StockComponent
import kotlinx.coroutines.flow.StateFlow

interface MainComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onClickNavigation(screen: Screen)

    sealed interface Child {
        data class HomeChild(val homeComponent: HomeComponent) : Child
        data class CatalogDetailsChild(val catalogDetailsComponent: CatalogDetailsComponent) : Child
        data class BasketChild(val basketComponent: BasketComponent) : Child
        data class StockChild(val stockComponent: StockComponent) : Child
        data class LoginFavoriteDetailsChild(
            val loginFavoriteDetailsComponent: LoginFavoriteDetailsComponent
        ) : Child
    }

    val screenState: StateFlow<Screen>
}