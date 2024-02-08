package com.github.gunin_igor75.onlineshopapp.presentation.favorite

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.usecase.DeleteFavoriteItemUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetFavoriteItemsUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.SaveFavoriteItemUseCase
import com.github.gunin_igor75.onlineshopapp.presentation.favorite.FavoriteStore.Intent
import com.github.gunin_igor75.onlineshopapp.presentation.favorite.FavoriteStore.Label
import com.github.gunin_igor75.onlineshopapp.presentation.favorite.FavoriteStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavoriteStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data class ClickItem(val item: Item) : Intent
        data class ClickChangeFavorite(val item: Item) : Intent
    }

    data class State(
        val items: List<Item>
    )

    sealed interface Label {
        data object ClickBack : Label
        data class ClickItem(val item: Item) : Label
    }
}

class FavoriteStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getFavoriteItemsUseCase: GetFavoriteItemsUseCase,
    private val saveFavoriteItemUseCase: SaveFavoriteItemUseCase,
    private val deleteFavoriteItemUseCase: DeleteFavoriteItemUseCase
) {

    fun create(userId: Long): FavoriteStore =
        object : FavoriteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavoriteStore",
            initialState = State(items = listOf()),
            bootstrapper = BootstrapperImpl(userId),
            executorFactory = { ExecutorImpl(userId) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class LoadedFavoritesItem(val items: List<Item>) : Action
    }

    private sealed interface Msg {
        data class LoadedFavoritesItem(val items: List<Item>) : Msg
    }

    private inner class BootstrapperImpl(private val userId: Long) :
        CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getFavoriteItemsUseCase(userId).collect {
                    dispatch(Action.LoadedFavoritesItem(it))
                }
            }
        }
    }

    private inner class ExecutorImpl(private val userId: Long) :
        CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                is Intent.ClickChangeFavorite -> {
                    val isFavorite = intent.item.isFavorite
                    if (isFavorite) {
                        scope.launch {
                            deleteFavoriteItemUseCase(userId, intent.item.id)
                        }
                    } else {
                        scope.launch {
                            saveFavoriteItemUseCase(userId, intent.item.id)
                        }
                    }
                }

                is Intent.ClickItem -> {
                    publish(Label.ClickItem(intent.item))
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.LoadedFavoritesItem -> {
                    dispatch(Msg.LoadedFavoritesItem(action.items))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.LoadedFavoritesItem -> {
                    copy(items = msg.items)
                }
            }
    }
}
