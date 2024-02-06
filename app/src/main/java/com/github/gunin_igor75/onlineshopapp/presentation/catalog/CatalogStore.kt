package com.github.gunin_igor75.onlineshopapp.presentation.catalog

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.usecase.DeleteFavoriteItemUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetChoseAllUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetChoseBodyUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetChoseFaceUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetChoseMaskUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetChoseSuntanUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetItemsUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetSortFeedbackDescUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetSortPriceAscUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetSortPriceDescUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.SaveFavoriteItemUseCase
import com.github.gunin_igor75.onlineshopapp.presentation.catalog.CatalogStore.Intent
import com.github.gunin_igor75.onlineshopapp.presentation.catalog.CatalogStore.Label
import com.github.gunin_igor75.onlineshopapp.presentation.catalog.CatalogStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface CatalogStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickSortFeedbackRating : Intent
        data object ClickSortPriceDesc : Intent
        data object ClickSortPriceAsc : Intent
        data object ClickChoseFace : Intent
        data object ClickChoseBody : Intent
        data object ClickChoseSuntan : Intent
        data object ClickChoseMask : Intent
        data object ClickChoseAll : Intent
        data class ClickChangeFavorite(val item: Item) : Intent
        data class ClickItem(val item: Item) : Intent

    }

    data class State(
        val itemsState: ItemState
    ) {
        sealed interface ItemState {
            data object Initial : ItemState
            data object Loading : ItemState
            data object Error : ItemState
            data class SuccessLoaded(val items: List<Item>) : ItemState
        }
    }

    sealed interface Label {
        data class ClickItem(val item: Item) : Label
    }
}

class CatalogStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getItemsUseCase: GetItemsUseCase,
    private val saveFavoriteItemUseCase: SaveFavoriteItemUseCase,
    private val deleteFavoriteItemUseCase: DeleteFavoriteItemUseCase,
    private val getSortFeedbackDescUseCase: GetSortFeedbackDescUseCase,
    private val getSortPriceDescUseCase: GetSortPriceDescUseCase,
    private val getSortPriceAscUseCase: GetSortPriceAscUseCase,
    private val getChoseBodyUseCase: GetChoseBodyUseCase,
    private val getChoseFaceUseCase: GetChoseFaceUseCase,
    private val getChoseSuntanUseCase: GetChoseSuntanUseCase,
    private val getChoseMaskUseCase: GetChoseMaskUseCase,
    private val getChoseAllUseCase: GetChoseAllUseCase
) {

    fun create(userId: Long): CatalogStore =
        object : CatalogStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CatalogStore",
            initialState = State(State.ItemState.Initial),
            bootstrapper = BootstrapperImpl(userId),
            executorFactory = { ExecutorImpl(userId) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object ItemsStartLoading : Action
        data class ItemsLoaded(val items: List<Item>) : Action
        data object ItemsError : Action
    }

    private sealed interface Msg {
        data object ItemsStartLoading : Msg
        data class ItemsLoaded(val items: List<Item>) : Msg
        data object ItemsError : Msg
    }

    private inner class BootstrapperImpl(
        val userId: Long,
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Action.ItemsStartLoading)
                try {
                    val items = getItemsUseCase(userId)
                    items.collect {
                        dispatch(Action.ItemsLoaded(it))
                    }
                } catch (e: Exception) {
                    dispatch(Action.ItemsError)
                }
            }
        }
    }

    private inner class ExecutorImpl(
        private val userId: Long
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ClickChangeFavorite -> {
                    scope.launch {
                        if (intent.item.isFavorite) {
                            deleteFavoriteItemUseCase(
                                userId = userId,
                                itemId = intent.item.id
                            )
                        } else {
                            saveFavoriteItemUseCase(
                                userId = userId,
                                itemId = intent.item.id
                            )
                        }
                        runIntent()
                    }
                }

                Intent.ClickChoseAll -> {
                    scope.launch {
                        getChoseAllUseCase()
                        runIntent()
                    }
                }

                is Intent.ClickItem -> {
                    publish(Label.ClickItem(intent.item))
                }

                Intent.ClickSortFeedbackRating -> {
                    scope.launch {
                        getSortFeedbackDescUseCase()
                        runIntent()
                    }
                }

                Intent.ClickSortPriceAsc -> {
                    scope.launch {
                        getSortPriceAscUseCase()
                        runIntent()
                    }
                }

                Intent.ClickSortPriceDesc -> {
                    scope.launch {
                        getSortPriceDescUseCase()
                        runIntent()
                    }
                }

                Intent.ClickChoseBody -> {
                    scope.launch {
                        getChoseBodyUseCase()
                        runIntent()
                    }
                }

                Intent.ClickChoseFace -> {
                    scope.launch {
                        getChoseFaceUseCase()
                        runIntent()
                    }
                }

                Intent.ClickChoseMask -> {
                    scope.launch {
                        getChoseMaskUseCase()
                        runIntent()
                    }
                }

                Intent.ClickChoseSuntan -> {
                    scope.launch {
                        getChoseSuntanUseCase()
                        runIntent()
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when(action){
                Action.ItemsError -> {
                    dispatch(Msg.ItemsError)
                }
                is Action.ItemsLoaded -> {
                    dispatch(Msg.ItemsLoaded(action.items))
                }
                Action.ItemsStartLoading -> {
                    dispatch(Msg.ItemsStartLoading)
                }
            }
        }

        private suspend fun runIntent() {
            dispatch(Msg.ItemsStartLoading)
            try {
                val items = this@CatalogStoreFactory.getItemsUseCase(userId)
                items.collect {
                    dispatch(Msg.ItemsLoaded(it))
                }
            } catch (e: Exception) {
                dispatch(Msg.ItemsError)
            }
        }
    }


    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                Msg.ItemsError -> {
                    copy(itemsState = State.ItemState.Error)
                }

                is Msg.ItemsLoaded -> {
                    copy(itemsState = State.ItemState.SuccessLoaded(msg.items))
                }

                Msg.ItemsStartLoading -> {
                    copy(itemsState = State.ItemState.Loading)
                }
            }
    }
}
