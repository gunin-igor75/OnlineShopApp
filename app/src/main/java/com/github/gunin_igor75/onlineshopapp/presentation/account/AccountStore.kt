package com.github.gunin_igor75.onlineshopapp.presentation.account

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.github.gunin_igor75.onlineshopapp.domain.entity.SignData
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.domain.usecase.ChangePhoneLengthUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetUserByIdUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.GetUserByPhoneUseCase
import com.github.gunin_igor75.onlineshopapp.domain.usecase.InsertUserUseCase
import com.github.gunin_igor75.onlineshopapp.extentions.isCheckPhone
import com.github.gunin_igor75.onlineshopapp.extentions.isCheckUsername
import com.github.gunin_igor75.onlineshopapp.presentation.account.AccountStore.Intent
import com.github.gunin_igor75.onlineshopapp.presentation.account.AccountStore.Label
import com.github.gunin_igor75.onlineshopapp.presentation.account.AccountStore.State
import com.github.gunin_igor75.onlineshopapp.presentation.main.OpenReason
import kotlinx.coroutines.launch
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
        val buttonState: ButtonState,
        val saveUserState: SaveUserState,
    ) {
        data class NameState(
            val name: String,
            val isError: Boolean,
        )

        data class LastnameState(
            val lastname: String,
            val isError: Boolean,
        )

        data class PhoneState(
            val phone: String,
            val isError: Boolean,
        )

        data class ButtonState(
            val enabled: Boolean,
        )

        data class SaveUserState(
            val isError: Boolean,
        )
    }

    sealed interface Label {
        data class ClickSaveUser(val user: User, val openReason: OpenReason) : Label
    }
}

class AccountStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val insertUserUseCase: InsertUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getUserByPhoneUseCase: GetUserByPhoneUseCase,
    private val changePhoneLengthUseCase: ChangePhoneLengthUseCase,
) {

    fun create(): AccountStore =
        object : AccountStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AccountStore",
            initialState = State(
                nameState = State.NameState("", false),
                lastnameState = State.LastnameState("", false),
                phoneState = State.PhoneState("", false),
                buttonState = State.ButtonState(false),
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
        data class ButtonState(val enabled: Boolean) : Msg
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
                    val state = getState()
                    val enabled = !isError
                            && (!state.lastnameState.isError && state.lastnameState.lastname.isNotEmpty())
                            && (!state.phoneState.isError && state.phoneState.phone.isNotEmpty())
                    dispatch(Msg.ButtonState(enabled))
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
                    val state = getState()
                    val enabled = !isError
                            && (!state.nameState.isError && state.nameState.name.isNotEmpty())
                            && (!state.phoneState.isError && state.phoneState.phone.isNotEmpty())
                    dispatch(Msg.ButtonState(enabled))
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
                    val state = getState()
                    val enabled = !isError
                            && (!state.lastnameState.isError && state.lastnameState.lastname.isNotEmpty())
                            && (!state.nameState.isError && state.nameState.name.isNotEmpty())
                    dispatch(Msg.ButtonState(enabled))
                }

                Intent.ClearLastname -> {
                    dispatch(Msg.ClearLastname)
                    dispatch(Msg.ButtonState(false))
                }

                Intent.ClearName -> {
                    dispatch(Msg.ClearName)
                    dispatch(Msg.ButtonState(false))
                }

                Intent.ClearPhone -> {
                    dispatch(Msg.ClearPhone)
                    dispatch(Msg.ButtonState(false))
                }

                is Intent.ClickSaveUser -> {
                    val state = getState()
                    val signData = SignData(
                        name = state.nameState.name,
                        lastname = state.lastnameState.lastname,
                        phone = state.phoneState.phone
                    )
                    changePhone(signData)
                    scope.launch {
                        val user = getUserByPhoneUseCase(signData.phone)
                        if (user == null) {
                            val userId = insertUserUseCase(signData)
                            val newUser =
                                getUserByIdUseCase(userId) ?: throw IllegalArgumentException(
                                    "User with id $userId not exists"
                                )
                            return@launch publish(Label.ClickSaveUser(newUser, OpenReason.FIRST))
                        }
                        if (user.name == signData.name && user.lastname == signData.lastname) {
                            return@launch publish(Label.ClickSaveUser(user, OpenReason.REPEATED))
                        }
                        dispatch(Msg.LogInError(true))
                        dispatch(Msg.ButtonState(false))
                    }
                }
            }
        }

        private fun changePhone(signData: SignData) {
            val isValid = changePhoneLengthUseCase(signData.phone)
            if (!isValid) {
                dispatch(
                    Msg.ChangePhone(
                        phone = signData.phone,
                        isError = true
                    )
                )
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
                        lastnameState = State.LastnameState(lastname = "", isError = false)
                    )
                }

                Msg.ClearName -> {
                    copy(
                        nameState = State.NameState(name = "", isError = false)
                    )
                }

                Msg.ClearPhone -> {
                    copy(
                        phoneState = State.PhoneState(phone = "", isError = false)
                    )
                }

                is Msg.LogInError -> {
                    copy(
                        saveUserState = State.SaveUserState(
                            isError = msg.isError
                        )
                    )
                }

                is Msg.ButtonState -> {
                    copy(
                        buttonState = State.ButtonState(msg.enabled)
                    )
                }
            }
    }
}

