package com.github.gunin_igor75.onlineshopapp.presentation.product

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.usecase.ObserveIsFavoriteUseCase
import com.github.gunin_igor75.onlineshopapp.presentation.product.ProductStore.Intent
import com.github.gunin_igor75.onlineshopapp.presentation.product.ProductStore.Label
import com.github.gunin_igor75.onlineshopapp.presentation.product.ProductStore.State
import javax.inject.Inject

interface ProductStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack: Intent
        data object ClickChangeIsFavorite: Intent
    }

    data class State(
        val item: Item,
        val isFavorite: Boolean
    )

    sealed interface Label {
        data object ClickBack: Label
    }
}

class ProductStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase
) {

    fun create(item: Item): ProductStore =
        object : ProductStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ProductStore",
            initialState = State(
                item = item,
                isFavorite = false
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class FavoriteChange(val isFavorite: Boolean): Action
    }

    private sealed interface Msg {
        data class FavoriteChange(val isFavorite: Boolean): Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {

        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
        }

        override fun executeAction(action: Action, getState: () -> State) {
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when(msg){
                is Msg.FavoriteChange -> TODO()
            }
    }
}
