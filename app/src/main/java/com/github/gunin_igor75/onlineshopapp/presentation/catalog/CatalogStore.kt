package com.github.gunin_igor75.onlineshopapp.presentation.catalog

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
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
        val items: List<Item>
    )

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

    fun create(user: User): CatalogStore =
        object : CatalogStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CatalogStore",
            initialState = State(items = listOf()),
            bootstrapper = BootstrapperImpl(user.id),
            executorFactory = { ExecutorImpl(user.id) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class ItemsLoaded(val items: List<Item>) : Action
        data class ItemsSortFeedbackRating(val items: List<Item>) : Action
        data class ItemsSortPriceDesc(val items: List<Item>) : Action
        data class ItemsSortPriceAsc(val items: List<Item>) : Action
        data class ItemsChoseFace(val items: List<Item>) : Action
        data class ItemsChoseBody(val items: List<Item>) : Action
        data class ItemsChoseSuntan(val items: List<Item>) : Action
        data class ItemsChoseMask(val items: List<Item>) : Action
        data class ItemsChoseAll(val items: List<Item>) : Action
        data class ItemsChangeFavorite(val items: List<Item>) : Action
    }

    private sealed interface Msg {
        data class ItemsLoaded(val items: List<Item>) : Msg
        data class ItemsSortFeedbackRating(val items: List<Item>) : Msg
        data class ItemsSortPriceDesc(val items: List<Item>) : Msg
        data class ItemsSortPriceAsc(val items: List<Item>) : Msg
        data class ItemsChoseFace(val items: List<Item>) : Msg
        data class ItemsChoseBody(val items: List<Item>) : Msg
        data class ItemsChoseSuntan(val items: List<Item>) : Msg
        data class ItemsChoseMask(val items: List<Item>) : Msg
        data class ItemsChoseAll(val items: List<Item>) : Msg
        data class ItemsChangeFavorite(val items: List<Item>) : Msg
    }

    private inner class BootstrapperImpl(
        val userId: Long,
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsLoaded(it))
                }
            }

            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsSortFeedbackRating(it))
                }
            }

            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsSortPriceDesc(it))
                }
            }

            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsSortPriceAsc(it))
                }
            }

            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsChoseAll(it))
                }
            }

            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsChoseBody(it))
                }
            }

            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsChoseFace(it))
                }
            }

            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsChoseMask(it))
                }
            }

            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsChoseSuntan(it))
                }
            }

            scope.launch {
                val items = getItemsUseCase(userId)
                items.collect {
                    dispatch(Action.ItemsChangeFavorite(it))
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
                    }
                }

                Intent.ClickChoseAll -> {
                    scope.launch {
                        getChoseAllUseCase()
                    }
                }

                is Intent.ClickItem -> {
                    publish(Label.ClickItem(intent.item))
                }

                Intent.ClickSortFeedbackRating -> {
                    getSortFeedbackDescUseCase()
                }

                Intent.ClickSortPriceAsc -> {
                    getSortPriceAscUseCase()
                }

                Intent.ClickSortPriceDesc -> {
                    getSortPriceDescUseCase()
                }

                Intent.ClickChoseBody -> {
                    scope.launch {
                        getChoseBodyUseCase()
                    }
                }

                Intent.ClickChoseFace -> {
                    scope.launch {
                        getChoseFaceUseCase()
                    }
                }

                Intent.ClickChoseMask -> {
                    scope.launch {
                        getChoseMaskUseCase()
                    }
                }

                Intent.ClickChoseSuntan -> {
                    scope.launch {
                        getChoseSuntanUseCase()
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.ItemsLoaded -> {
                    dispatch(Msg.ItemsLoaded(action.items))
                }

                is Action.ItemsChangeFavorite -> {
                    dispatch(Msg.ItemsChangeFavorite(action.items))
                }

                is Action.ItemsChoseAll -> {
                    dispatch(Msg.ItemsChoseAll(action.items))
                }

                is Action.ItemsChoseBody -> {
                    dispatch(Msg.ItemsChoseBody(action.items))
                }

                is Action.ItemsChoseFace -> {
                    dispatch(Msg.ItemsChoseFace(action.items))
                }

                is Action.ItemsChoseMask -> {
                    dispatch(Msg.ItemsChoseMask(action.items))
                }

                is Action.ItemsChoseSuntan -> {
                    dispatch(Msg.ItemsChoseSuntan(action.items))
                }

                is Action.ItemsSortFeedbackRating -> {
                    dispatch(Msg.ItemsSortFeedbackRating(action.items))
                }

                is Action.ItemsSortPriceAsc -> {
                    dispatch(Msg.ItemsSortPriceAsc(action.items))
                }

                is Action.ItemsSortPriceDesc -> {
                    dispatch(Msg.ItemsSortPriceDesc(action.items))
                }
            }
        }

    }


    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {

                is Msg.ItemsLoaded -> {
                    copy(items = msg.items)
                }

                is Msg.ItemsChangeFavorite -> {
                    copy(items = msg.items)
                }
                is Msg.ItemsChoseAll -> {
                    copy(items = msg.items)
                }
                is Msg.ItemsChoseBody -> {
                    copy(items = msg.items)
                }
                is Msg.ItemsChoseFace -> {
                    copy(items = msg.items)
                }
                is Msg.ItemsChoseMask -> {
                    copy(items = msg.items)
                }
                is Msg.ItemsChoseSuntan -> {
                    copy(items = msg.items)
                }
                is Msg.ItemsSortFeedbackRating -> {
                    copy(items = msg.items)
                }
                is Msg.ItemsSortPriceAsc -> {
                    copy(items = msg.items)
                }
                is Msg.ItemsSortPriceDesc -> {
                    copy(items = msg.items)
                }
            }
    }
}
