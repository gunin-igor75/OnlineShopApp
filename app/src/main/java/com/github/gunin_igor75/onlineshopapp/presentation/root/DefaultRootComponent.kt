package com.github.gunin_igor75.onlineshopapp.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.account.DefaultAccountComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.DefaultMainComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.OpenReason
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultRootComponent @AssistedInject constructor(
    private val defaultAccountComponent: DefaultAccountComponent.Factory,
    private val defaultMainComponent: DefaultMainComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Account,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext,
    ): RootComponent.Child {
        return when (config) {
            Config.Account -> {
                val component = defaultAccountComponent.create(
                    componentContext = componentContext,
                    onSaveUserClicked = { user, openReason ->
                        navigation.replaceAll(Config.Main(user, openReason))
                    }
                )
                RootComponent.Child.Account(component)
            }

            is Config.Main -> {
                val component = defaultMainComponent.create(
                    user = config.user,
                    openReason = config.openReason,
                    onLogoutClicked = {
                        navigation.replaceAll(Config.Account)
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Main(component)
            }
        }
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        data object Account : Config

        @Parcelize
        data class Main(
            val user: User,
            val openReason: OpenReason,
        ) : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultRootComponent
    }
}