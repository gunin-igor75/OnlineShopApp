package com.github.gunin_igor75.onlineshopapp.presentation.account

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.gunin_igor75.onlineshopapp.domain.entity.SignData
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetUserByIdUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetUserByPhoneUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.InsertUserUseCase
import com.github.gunin_igor75.onlineshopapp.ext.isCheckPhone
import com.github.gunin_igor75.onlineshopapp.ext.isCheckUsername
import com.github.gunin_igor75.onlineshopapp.presentation.account.AccountStore.Intent
import com.github.gunin_igor75.onlineshopapp.presentation.account.AccountStore.Label
import com.github.gunin_igor75.onlineshopapp.presentation.account.AccountStore.State
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

interface AccountStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChaneName(val name: String) : Intent
        data class ChangeLastname(val lastname: String) : Intent
        data class ChangePhone(val phone: String) : Intent
        data object ClearName : Intent
        data object ClearLastname : Intent
        data object ClearPhone : Intent
        data object ClickSaveUser : Intent
    }

    data class State(
        val nameState: NameState,
        val lastnameState: LastnameState,
        val phoneState: PhoneState,
        val saveUserState: SaveUserState,
        val isActiveButton: Boolean =
            (!nameState.isError && !lastnameState.isError && !phoneState.isError) &&
                    (nameState.name.isNotEmpty() && lastnameState.lastname.isNotEmpty() && phoneState.phone.isNotEmpty())

    ) {
        data class NameState(
            val name: String,
            val isError: Boolean
        )

        data class LastnameState(
            val lastname: String,
            val isError: Boolean
        )

        data class PhoneState(
            val phone: String,
            val isError: Boolean
        )

        data class SaveUserState(
            val isError: Boolean
        )
    }

    sealed interface Label {
        data class ClickSaveUserHome(val user: User) : Label
        data class ClickSaveUserCatalog(val user: User) : Label

    }
}

class AccountStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val insertUserUseCase: InsertUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getUserByPhoneUseCase: GetUserByPhoneUseCase
) {

    fun create(): AccountStore =
        object : AccountStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AccountStore",
            initialState = State(
                isActiveButton = false,
                nameState = State.NameState("", false),
                lastnameState = State.LastnameState("", false),
                phoneState = State.PhoneState("", false),
                saveUserState = State.SaveUserState(false)
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
        data class ChangeName(val name: String, val isError: Boolean) : Msg
        data class ChangeLastname(val lastname: String, val isError: Boolean) : Msg
        data class ChangePhone(val phone: String, val isError: Boolean) : Msg
        data object ClearName : Msg
        data object ClearLastname : Msg
        data object ClearPhone : Msg
        data class LogInError(val isError: Boolean) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChaneName -> {
                    val isError = intent.name.isCheckUsername()
                    val name = intent.name
                    dispatch(
                        Msg.ChangeName(
                            name = name,
                            isError = isError
                        )
                    )
                }

                is Intent.ChangeLastname -> {
                    val isError = intent.lastname.isCheckUsername()
                    val name = intent.lastname
                    dispatch(
                        Msg.ChangeLastname(
                            lastname = name,
                            isError = isError
                        )
                    )
                }

                is Intent.ChangePhone -> {
                    val isError = intent.phone.isCheckPhone()
                    val phone = intent.phone
                    dispatch(
                        Msg.ChangePhone(
                            phone = phone,
                            isError = isError
                        )
                    )
                }

                Intent.ClearLastname -> {
                    dispatch(Msg.ClearLastname)
                }

                Intent.ClearName -> {
                    dispatch(Msg.ClearName)
                }

                Intent.ClearPhone -> {
                    dispatch(Msg.ClearPhone)
                }

                is Intent.ClickSaveUser -> {
                    val state = getState()
                    val signData = SignData(
                        name = state.nameState.name,
                        lastname = state.lastnameState.lastname,
                        phone = state.phoneState.phone
                    )
                    scope.launch {
                        val user = getUserByPhoneUseCase(signData.phone)
                        if (user == null) {
                            val userId = insertUserUseCase(signData)
                            val newUser =
                                getUserByIdUseCase(userId) ?: throw IllegalArgumentException(
                                    "User with id $userId not exists"
                                )
                            return@launch publish(Label.ClickSaveUserHome(newUser))
                        }
                        if (user.name == signData.name && user.lastname == signData.lastname) {
                            return@launch publish(Label.ClickSaveUserCatalog(user))
                        }
                        dispatch(Msg.LogInError(true))
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ChangeLastname -> {
                    copy(
                        lastnameState = State.LastnameState(
                            lastname = msg.lastname,
                            isError = msg.isError
                        )
                    )
                }

                is Msg.ChangeName -> {
                    copy(
                        nameState = State.NameState(
                            name = msg.name,
                            isError = msg.isError
                        )
                    )
                }

                is Msg.ChangePhone -> {
                    copy(
                        phoneState = State.PhoneState(
                            phone = msg.phone,
                            isError = msg.isError
                        )
                    )
                }

                Msg.ClearLastname -> {
                    copy(
                        isActiveButton = false,
                        lastnameState = State.LastnameState(lastname = "", isError = false)
                    )
                }

                Msg.ClearName -> {
                    copy(
                        isActiveButton = false,
                        nameState = State.NameState(name = "", isError = false)
                    )
                }

                Msg.ClearPhone -> {
                    copy(
                        isActiveButton = false,
                        phoneState = State.PhoneState(phone = "", isError = false)
                    )
                }

                is Msg.LogInError -> {
                    copy(
                        isActiveButton = false,
                        saveUserState = State.SaveUserState(
                            isError = msg.isError
                        )
                    )
                }
            }
    }
}

