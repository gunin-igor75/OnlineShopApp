package com.github.gunin_igor75.onlineshopapp.presentation.login_favorite_details

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.details.DefaultDetailsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.favorite.DefaultFavoriteComponent
import com.github.gunin_igor75.onlineshopapp.presentation.login.DefaultLoginComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultLoginFavoriteDetailsComponent @AssistedInject constructor(
    private val defaultLoginComponent: DefaultLoginComponent.Factory,
    private val defaultFavoriteComponent: DefaultFavoriteComponent.Factory,
    private val defaultDetailsComponent: DefaultDetailsComponent.Factory,
    @Assisted("user") private val user: User,
    @Assisted("componentContext") componentContext: ComponentContext
) : LoginFavoriteDetailsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ChildConfig>()

    override val childStack: Value<ChildStack<*, LoginFavoriteDetailsComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = ChildConfig.Login(user),
        handleBackButton = true,
        childFactory = ::child
    )


    private fun child(
        config: ChildConfig,
        componentContext: ComponentContext
    ): LoginFavoriteDetailsComponent.Child {
        return when (config) {
            is ChildConfig.Favorite -> {
                val component = defaultFavoriteComponent.create(
                    user = config.user,
                    onBackClicked = {
                        navigation.pop()
                    },
                    onItemClicked = {
                        navigation.push(ChildConfig.Details(config.user, it))
                    },
                    componentContext = componentContext
                )
                LoginFavoriteDetailsComponent.Child.FavoriteChild(component)
            }

            is ChildConfig.Login -> {
                val component = defaultLoginComponent.create(
                    user = config.user,
                    onLogoutClicked = {
                        navigation.popTo(0)
                    },
                    onFavoriteClicked = {
                        navigation.push(ChildConfig.Favorite(config.user))
                    },
                    componentContext = componentContext
                )
                LoginFavoriteDetailsComponent.Child.LoginChild(component)
            }

            is ChildConfig.Details -> {
                val component = defaultDetailsComponent.create(
                    user = config.user,
                    item = config.item,
                    onBackClicked = {
                        navigation.pop()
                    },
                    componentContext = componentContext
                )
                LoginFavoriteDetailsComponent.Child.DetailsChild(component)
            }
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        data class Login(val user: User) : ChildConfig

        @Parcelize
        data class Favorite(val user: User) : ChildConfig

        @Parcelize
        data class Details(val user: User, val item: Item) : ChildConfig
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("user") user: User,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultLoginFavoriteDetailsComponent
    }
}