package com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.catalog.CatalogComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details.DetailsComponent

interface CatalogDetailsComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child{
        data class CatalogChild(val catalogComponent: CatalogComponent): Child
        data class DetailsChild(val detailsComponent: DetailsComponent): Child
    }
}