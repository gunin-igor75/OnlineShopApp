package com.github.gunin_igor75.onlineshopapp.presentation.product

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.gunin_igor75.onlineshopapp.presentation.product.ProductStore.Intent
import com.github.gunin_igor75.onlineshopapp.presentation.product.ProductStore.Label
import com.github.gunin_igor75.onlineshopapp.presentation.product.ProductStore.State

internal interface ProductStore : Store<Intent, State, Label> {

    sealed interface Intent {
    }

    data class State(val unit: Unit)

    sealed interface Label {
    }
}

internal class ProductStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): ProductStore =
        object : ProductStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ProductStore",
            initialState = State(Unit),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
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
        override fun State.reduce(message: Msg): State = State(Unit)
    }
}
