package com.github.gunin_igor75.onlineshopapp.presentation.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.extentions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultLoginComponent @AssistedInject constructor(
    private val loginStoreFactory: LoginStoreFactory,
    @Assisted("user") private val user: User,
    @Assisted("onLogoutClicked") private val onLogoutClicked: () -> Unit,
    @Assisted("onFavoriteClicked") private val onFavoriteClicked: (Long) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : LoginComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { loginStoreFactory.create(user) }

    private val componentScope = componentScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    val model: StateFlow<LoginStore.State> = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect {
                when (it) {

                    is LoginStore.Label.ClickFavorite -> {
                        onFavoriteClicked(it.userId)
                    }

                    LoginStore.Label.ClickLogOut -> {
                        onLogoutClicked()
                    }
                }
            }
        }
    }


    override fun onClickLogOut() {
        store.accept(LoginStore.Intent.ClickLogOut)
    }

    override fun onClickFavorite(userId: Long) {
        store.accept(LoginStore.Intent.ClickFavorite(userId))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("user") user: User,
            @Assisted("onLogoutClicked") onLogoutClicked: () -> Unit,
            @Assisted("onFavoriteClicked") onFavoriteClicked: (Long) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultLoginComponent
    }
}