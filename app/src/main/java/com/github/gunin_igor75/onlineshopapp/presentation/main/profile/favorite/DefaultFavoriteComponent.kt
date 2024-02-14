package com.github.gunin_igor75.onlineshopapp.presentation.main.profile.favorite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.extentions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultFavoriteComponent @AssistedInject constructor(
    private val favoriteStoreFactory: FavoriteStoreFactory,
    @Assisted("user") private val user: User,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("onItemClicked") private val onItemClicked: (Item) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
) : FavoriteComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { favoriteStoreFactory.create(user) }

    private val componentScope = componentScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavoriteStore.State> = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect {
                when (it) {
                    FavoriteStore.Label.ClickBack -> {
                        onBackClicked()
                    }

                    is FavoriteStore.Label.ClickItem -> {
                        onItemClicked(it.item)
                    }
                }
            }
        }
    }

    override fun onClickBack() {
        store.accept(FavoriteStore.Intent.ClickBack)
    }

    override fun onClickItem(item: Item) {
        store.accept(FavoriteStore.Intent.ClickItem(item))
    }

    override fun onChangeFavorite(item: Item) {
        store.accept(FavoriteStore.Intent.ClickChangeFavorite(item))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("user") user: User,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("onItemClicked") onItemClicked: (Item) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultFavoriteComponent
    }
}