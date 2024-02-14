package com.github.gunin_igor75.onlineshopapp.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.account.AutoVisualTransformation.Companion.MASK
import com.github.gunin_igor75.onlineshopapp.presentation.account.AutoVisualTransformation.Companion.MASK_CHAR_INPUT
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AccountContent(
    component: AccountComponent,
    modifier: Modifier = Modifier
) {
    val state by component.model.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp
            )
        )
    }
}

@Composable
private fun TextFieldText(
    value: String,
    labelId: Int,
    placeholderId: Int,
    isError: Boolean,
    onChangeValue: (String) -> Unit,
    onClearValue: () -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onChangeValue(it) },
        label = { Text(text = stringResource(id = labelId)) },
        placeholder = { Text(text = stringResource(id = placeholderId)) },
        supportingText = {
            if (isError) Text(text = stringResource(R.string.is_error_message))
        },
        isError = isError,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onClearValue() }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(R.string.button_clear_value_description)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}

@Composable
private fun TextFieldPhone(
    value: String,
    labelId: Int,
    placeholderId: Int,
    isError: Boolean,
    onChangeValue: (String) -> Unit,
    onClearValue: () -> Unit
) {
    val inputMaxLength = MASK.count { maskChar -> maskChar == MASK_CHAR_INPUT }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { currentValue ->
            val result = currentValue.take(inputMaxLength).filter { it.isDigit() }
            onChangeValue(result)
        },
        label = { Text(text = stringResource(id = labelId)) },
        placeholder = { Text(text = stringResource(id = placeholderId)) },
        supportingText = {
            if (isError) Text(text = stringResource(R.string.is_error_message))
        },
        isError = isError,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onClearValue() }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(R.string.button_clear_value_description)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = AutoVisualTransformation(
            mask = MASK,
            maskCharInput = MASK_CHAR_INPUT,
            enableCursorMove = false
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AccountContentPreview(
) {
    OnlineShopAppTheme{
        AccountContent(
            component = AccountComponentPreview(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

private class AccountComponentPreview: AccountComponent{
    override val model: StateFlow<AccountStore.State> =
        MutableStateFlow(
            AccountStore.State(
                nameState = AccountStore.State.NameState("", false ),
                lastnameState = AccountStore.State.LastnameState("", false ),
                phoneState = AccountStore.State.PhoneState("", false ),
                saveUserState = AccountStore.State.SaveUserState( false )
            )
        )
    override fun onChangeName(name: String) {}
    override fun onChangeLastname(lastname: String) { }
    override fun onChangePhone(phone: String) { }
    override fun onClearName() { }
    override fun onClearLastname() { }
    override fun onClearPhone() { }
    override fun onClickLogin(user: User) { }

}