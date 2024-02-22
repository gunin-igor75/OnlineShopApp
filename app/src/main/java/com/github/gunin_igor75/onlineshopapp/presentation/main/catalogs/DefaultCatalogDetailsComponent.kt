package com.github.gunin_igor75.onlineshopapp.presentation.catalog_details

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.CatalogDetailsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.catalog.DefaultCatalogComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details.DefaultDetailsComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultCatalogDetailsComponent @AssistedInject constructor(
    private val catalogComponentFactory: DefaultCatalogComponent.Factory,
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    @Assisted("user") private val user: User,
    @Assisted("componentContext") componentContext: ComponentContext
) : CatalogDetailsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ChildConfig>()

    override val childStack: Value<ChildStack<*, CatalogDetailsComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = ChildConfig.Catalog(user),
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: ChildConfig,
        componentContext: ComponentContext
    ): CatalogDetailsComponent.Child {
        return when (config) {
            is ChildConfig.Catalog -> {
                val component = catalogComponentFactory.create(
                    user = user,
                    componentContext = componentContext,
                    onItemClicked = {
                        navigation.push(ChildConfig.Details(config.user, it))
                    }
                )
                CatalogDetailsComponent.Child.CatalogChild(component)
            }

            is ChildConfig.Details -> {
                val component = detailsComponentFactory.create(
                    user = config.user,
                    item = config.item,
                    componentContext = componentContext,
                    onBackClicked = {
                        navigation.pop()
                    }
                )
                CatalogDetailsComponent.Child.DetailsChild(component)
            }
       }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        data class Catalog(val user: User) : ChildConfig

        @Parcelize
        data class Details(val user: User, val item: Item) : ChildConfig
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("user") user: User,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultCatalogDetailsComponent
    }
}