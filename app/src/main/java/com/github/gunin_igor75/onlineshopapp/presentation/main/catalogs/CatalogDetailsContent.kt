package com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.catalog.CatalogContent
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details.DetailsContent

@Composable
fun CatalogDetailsContent(
    paddingValues: PaddingValues,
    component: CatalogDetailsComponent
) {
    Children(stack = component.childStack) {
        when(val instance = it.instance){
            is CatalogDetailsComponent.Child.CatalogChild -> {
                CatalogContent(
                    modifier = Modifier.padding(paddingValues),
                    component = instance.catalogComponent
                )
            }
            is CatalogDetailsComponent.Child.DetailsChild -> {
                DetailsContent(
                    modifier = Modifier.padding(paddingValues),
                    component = instance.detailsComponent
                )
            }
        }
    }
}