package com.github.gunin_igor75.onlineshopapp.presentation.catalog

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

class DefaultCatalogComponent @AssistedInject constructor(
    private val catalogStoreFactory: CatalogStoreFactory,
    @Assisted("user") private val user: User,
    @Assisted("onItemClicked") private val onItemClicked: (Item) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : CatalogComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { catalogStoreFactory.create(user) }

    private val componentScope = componentScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    val model: StateFlow<CatalogStore.State> = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect {
                when (it) {
                    is CatalogStore.Label.ClickItem -> {
                        onItemClicked(it.item)
                    }
                }
            }
        }
    }

    override fun sortFeedbackRating() {
        store.accept(CatalogStore.Intent.ClickSortFeedbackRating)
    }

    override fun sortPriceDesc() {
        store.accept(CatalogStore.Intent.ClickSortPriceDesc)
    }

    override fun sortPriceAsc() {
        store.accept(CatalogStore.Intent.ClickSortPriceAsc)
    }

    override fun choseFace() {
        store.accept(CatalogStore.Intent.ClickChoseFace)
    }

    override fun choseBody() {
        store.accept(CatalogStore.Intent.ClickChoseBody)
    }

    override fun choseSuntan() {
        store.accept(CatalogStore.Intent.ClickChoseSuntan)
    }

    override fun choseMask() {
        store.accept(CatalogStore.Intent.ClickChoseMask)
    }

    override fun choseAll() {
        store.accept(CatalogStore.Intent.ClickChoseAll)
    }

    override fun changeFavorite(item: Item) {
        store.accept(CatalogStore.Intent.ClickChangeFavorite(item))
    }

    override fun onItem(item: Item) {
        store.accept(CatalogStore.Intent.ClickItem(item))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("user") user: User,
            @Assisted("onItemClicked") onItemClicked: (Item) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultCatalogComponent
    }
}