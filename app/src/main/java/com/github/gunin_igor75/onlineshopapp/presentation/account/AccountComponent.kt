package com.github.gunin_igor75.onlineshopapp.presentation.account

import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import kotlinx.coroutines.flow.StateFlow

interface AccountComponent {

    val model: StateFlow<AccountStore.State>
    fun onChangeName(name: String)
    fun onChangeLastname(lastname: String)
    fun onChangePhone(phone: String)
    fun onClearName()
    fun onClearLastname()
    fun onClearPhone()
    fun onClickLogin()
}