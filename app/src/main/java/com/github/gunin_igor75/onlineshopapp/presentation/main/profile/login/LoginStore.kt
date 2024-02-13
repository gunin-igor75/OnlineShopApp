package com.github.gunin_igor75.onlineshopapp.presentation.main.profile.login

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.domain.usecase.DeleteAllInfoUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetCountProductUseCase
import com.github.gunin_igor75.onlineshopapp.presentation.main.profile.login.LoginStore.Intent
import com.github.gunin_igor75.onlineshopapp.presentation.main.profile.login.LoginStore.Label
import com.github.gunin_igor75.onlineshopapp.presentation.main.profile.login.LoginStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject
interface LoginStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickLogOut : Intent
        data class ClickFavorite(val userId: Long): Intent
    }

    data class State(
        val user: User,
        val countProduct: String
    )

    sealed interface Label {
        data object ClickLogOut : Label
        data class ClickFavorite(val userId: Long): Label
    }
}

class LoginStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getCountProductUseCase: GetCountProductUseCase,
    private val deleteAllInfoUseCase: DeleteAllInfoUseCase
) {

    fun create(user: User): LoginStore =
        object : LoginStore, Store<Intent, State, Label> by storeFactory.create(
            name = "LoginStore",
            initialState = State(
                user = user,
                countProduct = ""
            ),
            bootstrapper = BootstrapperImpl(user.id),
            executorFactory = LoginStoreFactory::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class FavoriteCount(val countProduct: String) : Action
    }

    private sealed interface Msg {
        data class FavoriteCount(val countProduct: String) : Msg
    }

    private inner class BootstrapperImpl(private val userId: Long) :
        CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getCountProductUseCase(userId).collect {
                    dispatch(Action.FavoriteCount(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {

                Intent.ClickLogOut -> {
                    scope.launch {
                        deleteAllInfoUseCase()
                        publish(Label.ClickLogOut)
                    }
                }

                is Intent.ClickFavorite -> {
                    publish(Label.ClickFavorite(intent.userId))
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavoriteCount -> {
                    dispatch(Msg.FavoriteCount(action.countProduct))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.FavoriteCount -> {
                    copy(countProduct = msg.countProduct)
                }
            }
    }
}
