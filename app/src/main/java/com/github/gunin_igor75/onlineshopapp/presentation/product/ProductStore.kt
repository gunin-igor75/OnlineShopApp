package com.github.gunin_igor75.onlineshopapp.presentation.product

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.domain.usecase.DeleteFavoriteItemUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.ObserveIsFavoriteUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.SaveFavoriteItemUseCase
import com.github.gunin_igor75.onlineshopapp.presentation.product.ProductStore.Intent
import com.github.gunin_igor75.onlineshopapp.presentation.product.ProductStore.Label
import com.github.gunin_igor75.onlineshopapp.presentation.product.ProductStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ProductStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data object ClickChangeFavorite : Intent
    }

    data class State(
        val item: Item,
        val isFavorite: Boolean
    )

    sealed interface Label {
        data object ClickBack : Label
    }
}

class ProductStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase,
    private val saveFavoriteItemUseCase: SaveFavoriteItemUseCase,
    private val deleteFavoriteItemUseCase: DeleteFavoriteItemUseCase
) {

    fun create(
        user: User,
        item: Item
    ): ProductStore =
        object : ProductStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ProductStore",
            initialState = State(
                item = item,
                isFavorite = false
            ),
            bootstrapper = BootstrapperImpl(user.id, item.id),
            executorFactory = { ExecutorImpl(user.id) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class FavoriteChange(val isFavorite: Boolean) : Action
    }

    private sealed interface Msg {
        data class FavoriteChange(val isFavorite: Boolean) : Msg
    }

    private inner class BootstrapperImpl(
        private val userId: Long,
        private val itemId: String
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeIsFavoriteUseCase(
                    userId = userId,
                    itemId = itemId
                ).collect {
                    dispatch(Action.FavoriteChange(it))
                }
            }
        }
    }

    private inner class ExecutorImpl(
        private val userId: Long
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                Intent.ClickChangeFavorite -> {
                    val state = getState()
                    val isFavorite = state.isFavorite
                    val itemId = state.item.id
                    if (isFavorite) {
                        scope.launch {
                            deleteFavoriteItemUseCase(userId, itemId)
                        }
                    } else {
                        scope.launch {
                            saveFavoriteItemUseCase(userId, itemId)
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavoriteChange -> {
                    dispatch(Msg.FavoriteChange(action.isFavorite))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.FavoriteChange -> {
                    copy(isFavorite = msg.isFavorite)
                }
            }
    }
}
