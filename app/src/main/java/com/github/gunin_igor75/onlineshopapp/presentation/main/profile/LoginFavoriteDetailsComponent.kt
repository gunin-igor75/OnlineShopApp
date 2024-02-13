package com.github.gunin_igor75.onlineshopapp.presentation.login_favorite_details

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.presentation.details.DetailsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.favorite.FavoriteComponent
import com.github.gunin_igor75.onlineshopapp.presentation.login.LoginComponent

interface LoginFavoriteDetailsComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class LoginChild(val loginComponent: LoginComponent): Child
        data class FavoriteChild(val favoriteComponent: FavoriteComponent): Child
        data class DetailsChild(val detailsComponent: DetailsComponent): Child

    }
}