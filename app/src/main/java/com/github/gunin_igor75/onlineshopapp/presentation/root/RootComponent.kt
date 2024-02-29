package com.github.gunin_igor75.onlineshopapp.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.account.AccountComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.MainComponent
import com.github.gunin_igor75.onlineshopapp.presentation.main.OpenReason

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>
    sealed interface Child {
        data class Account(val accountComponent: AccountComponent): Child
        data class Main(val mainComponent: MainComponent) : Child
    }
}