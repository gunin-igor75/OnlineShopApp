package com.github.gunin_igor75.onlineshopapp.presentation.main

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.DefaultCatalogDetailsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.profile.DefaultLoginFavoriteDetailsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.basket.DefaultBasketComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.home.DefaultHomeComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.stock.DefaultStockComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

class DefaultMainComponent @AssistedInject constructor(
    private val defaultCatalogDetailsComponent: DefaultCatalogDetailsComponent.Factory,
    private val defaultLoginFavoriteDetailsComponent: DefaultLoginFavoriteDetailsComponent.Factory,
    @Assisted("user") private val user: User,
    @Assisted("openReason") private val openReason: OpenReason,
    @Assisted("onLogoutClicked") private val onLogoutClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
) : MainComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ChildConfig>()

    private val _screenState = MutableStateFlow<Screen>(Screen.Home)

    override val screenState: StateFlow<Screen> = _screenState

    override val childStack: Value<ChildStack<*, MainComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = choseOpenReason(),
        handleBackButton = true,
        childFactory = ::child
    )

    override fun onClickNavigation(screen: Screen) {
        _screenState.value = screen
        val config = choseScreen(screen)
        navigation.bringToFront(config)
    }

    private fun choseOpenReason(): ChildConfig {
        return when (openReason) {
            OpenReason.FIRST -> {
                _screenState.value = Screen.Home
                ChildConfig.Home(user)
            }

            OpenReason.REPEATED -> {
                _screenState.value = Screen.Catalog
                ChildConfig.CatalogDetails(user)
            }
        }
    }

    private fun child(
        config: ChildConfig,
        componentContext: ComponentContext,
    ): MainComponent.Child {
        return when (config) {
            is ChildConfig.Basket -> {
                val component = DefaultBasketComponent()
                MainComponent.Child.BasketChild(component)
            }

            is ChildConfig.CatalogDetails -> {
                val component = defaultCatalogDetailsComponent.create(
                    user = config.user,
                    componentContext = componentContext
                )
                MainComponent.Child.CatalogDetailsChild(component)
            }

            is ChildConfig.Home -> {
                val component = DefaultHomeComponent()
                MainComponent.Child.HomeChild(component)
            }

            is ChildConfig.LoginFavoriteDetails -> {
                val component = defaultLoginFavoriteDetailsComponent.create(
                    user = config.user,
                    onLogoutClicked = onLogoutClicked,
                    componentContext = componentContext
                )
                MainComponent.Child.LoginFavoriteDetailsChild(component)
            }

            is ChildConfig.Stock -> {
                val component = DefaultStockComponent()
                MainComponent.Child.StockChild(component)
            }
        }
    }

    private fun choseScreen(screen: Screen): ChildConfig {
        return when (screen) {
            Screen.Home -> ChildConfig.Home(user)
            Screen.Catalog -> ChildConfig.CatalogDetails(user)
            Screen.Basket -> ChildConfig.Basket(user)
            Screen.Stock -> ChildConfig.Stock(user)
            Screen.Login -> ChildConfig.LoginFavoriteDetails(user)
        }
    }

    private sealed interface ChildConfig : Parcelable {
        @Parcelize
        data class Home(val user: User) : ChildConfig

        @Parcelize
        data class CatalogDetails(val user: User) : ChildConfig

        @Parcelize
        data class Basket(val user: User) : ChildConfig

        @Parcelize
        data class Stock(val user: User) : ChildConfig

        @Parcelize
        data class LoginFavoriteDetails(val user: User) : ChildConfig
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("user") user: User,
            @Assisted("openReason") openReason: OpenReason,
            @Assisted("onLogoutClicked") onLogoutClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultMainComponent
    }
}