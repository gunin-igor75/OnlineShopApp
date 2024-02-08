package com.github.gunin_igor75.onlineshopapp.presentation.account

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.extentions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultAccountComponent @AssistedInject constructor(
    private val accountStoreFactory: AccountStoreFactory,
    @Assisted("onSaveUserCatalog") private val onSaveUserCatalog: (User) -> Unit,
    @Assisted("onSaveUserHome") private val onSaveUserHome: (User) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : AccountComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { accountStoreFactory.create() }

    private val componentScope = componentScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    val model: StateFlow<AccountStore.State> = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect {
                when (it) {
                    is AccountStore.Label.ClickSaveUserCatalog -> {
                        onSaveUserCatalog(it.user)
                    }

                    is AccountStore.Label.ClickSaveUserHome -> {
                        onSaveUserHome(it.user)
                    }
                }
            }
        }
    }

    override fun onChangeName(name: String) {
        store.accept(AccountStore.Intent.ChaneName(name))
    }

    override fun onChangeLastname(lastname: String) {
        store.accept(AccountStore.Intent.ChangeLastname(lastname))
    }

    override fun onChangePhone(phone: String) {
        store.accept(AccountStore.Intent.ChangePhone(phone))
    }

    override fun onClearName() {
        store.accept(AccountStore.Intent.ClearName)
    }

    override fun onClearLastname() {
        store.accept(AccountStore.Intent.ClearLastname)
    }

    override fun onClearPhone() {
        store.accept(AccountStore.Intent.ClearPhone)
    }

    override fun onClickLogin(user: User) {
        store.accept(AccountStore.Intent.ClickSaveUser)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onSaveUserCatalog") onSaveUserCatalog: (User) -> Unit,
            @Assisted("onSaveUserHome") onSaveUserHome: (User) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultAccountComponent
    }
}