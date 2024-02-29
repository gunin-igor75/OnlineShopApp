package com.github.gunin_igor75.onlineshopapp.presentation.main.profile

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details.DetailsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.profile.favorite.FavoriteComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.profile.login.LoginComponent

interface LoginFavoriteDetailsComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class LoginChild(val loginComponent: LoginComponent): Child
        data class FavoriteChild(val favoriteComponent: FavoriteComponent): Child
        data class DetailsChild(val detailsComponent: DetailsComponent): Child

    }
}