package com.github.gunin_igor75.onlineshopapp.presentation.main.profile.login

import kotlinx.coroutines.flow.StateFlow

interface LoginComponent {

    val model: StateFlow<LoginStore.State>
    fun onClickLogOut()
    fun onClickFavorite(userId: Long)
}