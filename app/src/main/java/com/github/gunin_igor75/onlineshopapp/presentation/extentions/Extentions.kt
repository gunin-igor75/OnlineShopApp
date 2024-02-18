package com.github.gunin_igor75.onlineshopapp.presentation.extentions

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel


fun ComponentContext.componentScope() = CoroutineScope(
    Dispatchers.Main.immediate + SupervisorJob()
).apply {
    lifecycle.doOnDestroy {
        cancel()
    }
}

fun String.convertPhone(): String =
    String.format("+7 ${this.substring(0, 3)} ${this.substring(3, 6)}" +
            " ${this.substring(6, 8)} ${this.substring(8)}")