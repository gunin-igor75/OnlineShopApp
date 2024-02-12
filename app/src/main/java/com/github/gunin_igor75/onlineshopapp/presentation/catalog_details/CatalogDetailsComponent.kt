package com.github.gunin_igor75.onlineshopapp.presentation.catalog_details

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.presentation.catalog.CatalogComponent
import com.github.gunin_igor75.onlineshopapp.presentation.details.DetailsComponent

interface CatalogDetailsComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child{
        data class CatalogChild(val catalogComponent: CatalogComponent): Child
        data class DetailsChild(val detailsComponent: DetailsComponent): Child
    }
}