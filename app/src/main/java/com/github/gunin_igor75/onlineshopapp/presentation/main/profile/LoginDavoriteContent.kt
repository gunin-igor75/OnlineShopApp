package com.github.gunin_igor75.onlineshopapp.presentation.main.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details.DetailsContent
import com.github.gunin_igor75.onlineshopapp.presentation.main.profile.favorite.FavoriteContent
import com.github.gunin_igor75.onlineshopapp.presentation.main.profile.login.LoginContent

@Composable
fun LoginFavoriteDetailsContent(
    paddingValues: PaddingValues = PaddingValues(),
    component: LoginFavoriteDetailsComponent
) {
    Children(stack = component.childStack) {
        when(val instance = it.instance){
            is LoginFavoriteDetailsComponent.Child.DetailsChild -> {
                DetailsContent(
                    modifier = Modifier.padding(paddingValues),
                    component = instance.detailsComponent
                )
            }
            is LoginFavoriteDetailsComponent.Child.FavoriteChild -> {
                FavoriteContent(component = instance.favoriteComponent)
            }
            is LoginFavoriteDetailsComponent.Child.LoginChild -> {
                LoginContent(
                    paddingValues = paddingValues,
                    component = instance.loginComponent
                )
            }
        }
    }
}