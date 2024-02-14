package com.github.gunin_igor75.onlineshopapp.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.github.gunin_igor75.onlineshopapp.presentation.account.AccountContent
import com.github.gunin_igor75.onlineshopapp.presentation.main.MainContent

@Composable
fun RootContent(
    component: RootComponent
) {
    Children(stack = component.stack) {
        when(val instance = it.instance){
            is RootComponent.Child.Account -> {
                AccountContent(component = instance.accountComponent)
            }
            is RootComponent.Child.Main -> {
                MainContent(component = instance.mainComponent)
            }
        }
    }
}