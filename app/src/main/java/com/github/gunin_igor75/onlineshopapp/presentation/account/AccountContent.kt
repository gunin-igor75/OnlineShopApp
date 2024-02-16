package com.github.gunin_igor75.onlineshopapp.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.account.AutoVisualTransformation.Companion.MASK
import com.github.gunin_igor75.onlineshopapp.presentation.account.AutoVisualTransformation.Companion.MASK_CHAR_INPUT
import com.github.gunin_igor75.onlineshopapp.presentation.component.TopBarApp
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AccountContent(
    component: AccountComponent,
    modifier: Modifier = Modifier
) {
    val state by component.model.collectAsState()

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarApp(R.string.login)
        Spacer(modifier = Modifier.weight(1f))
        TextFieldText(
            modifier = Modifier.fillMaxWidth(),
            value = state.nameState.name,
            labelId = R.string.label_name,
            placeholderId = R.string.placeholder_name,
            isError = state.nameState.isError,
            onChangeValue = component::onChangeName,
            onClearValue = component::onClearName
        )
        TextFieldText(
            modifier = Modifier.fillMaxWidth(),
            value = state.lastnameState.lastname,
            labelId = R.string.label_lastname,
            placeholderId = R.string.placeholder_lastname,
            isError = state.lastnameState.isError,
            onChangeValue = component::onChangeLastname,
            onClearValue = component::onClearLastname
        )
        TextFieldPhone(
            modifier = Modifier.fillMaxWidth(),
            value = state.phoneState.phone,
            labelId = R.string.label_phone,
            placeholderId = R.string.placeholder_phone,
            isError = state.phoneState.isError,
            onChangeValue = component::onChangePhone,
            onClearValue = component::onClearPhone
        )
        val shape = RoundedCornerShape(8.dp)
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            onClick = {}
        ) {
            Text(text = stringResource(id = R.string.login_button))
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = getBottomText(),
            textAlign = TextAlign.Center,
            lineHeight = 10.sp
        )
    }
}



@Composable
private fun getBottomText(): AnnotatedString = buildAnnotatedString {
    withStyle(SpanStyle(fontSize = 10.sp)) {
        append(stringResource(id = R.string.account_text_bottom_part_1))
        append("\n")
        withStyle(
            SpanStyle(textDecoration = TextDecoration.Underline)
        ) {
            append(stringResource(id = R.string.account_text_bottom_part_2))
        }
    }
}

@Composable
private fun TextFieldText(
    modifier: Modifier = Modifier,
    value: String,
    labelId: Int,
    placeholderId: Int,
    isError: Boolean,
    onChangeValue: (String) -> Unit,
    onClearValue: () -> Unit
) {
    TextField(
        modifier = modifier,
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
    modifier: Modifier = Modifier,
    value: String,
    labelId: Int,
    placeholderId: Int,
    isError: Boolean,
    onChangeValue: (String) -> Unit,
    onClearValue: () -> Unit
) {
    val inputMaxLength = MASK.count { maskChar -> maskChar == MASK_CHAR_INPUT }

    TextField(
        modifier = modifier,
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
private fun AccountContentPreview(
) {
    OnlineShopAppTheme {
        AccountContent(
            component = AccountComponentPreview(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarAppPreview() {
    OnlineShopAppTheme {
        TopBarApp(R.string.login)
    }
}

private class AccountComponentPreview : AccountComponent {
    override val model: StateFlow<AccountStore.State> =
        MutableStateFlow(
            AccountStore.State(
                nameState = AccountStore.State.NameState("", false),
                lastnameState = AccountStore.State.LastnameState("Пупкин", false),
                phoneState = AccountStore.State.PhoneState("", false),
                saveUserState = AccountStore.State.SaveUserState(false)
            )
        )

    override fun onChangeName(name: String) {}
    override fun onChangeLastname(lastname: String) {}
    override fun onChangePhone(phone: String) {}
    override fun onClearName() {}
    override fun onClearLastname() {}
    override fun onClearPhone() {}
    override fun onClickLogin(user: User) {}

}