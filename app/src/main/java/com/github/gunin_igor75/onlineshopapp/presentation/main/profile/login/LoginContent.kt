package com.github.gunin_igor75.onlineshopapp.presentation.main.profile.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.User
import com.github.gunin_igor75.onlineshopapp.presentation.extentions.convertPhone
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Grey
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.GreyLight
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Orange
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Pink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginContent(
    component: LoginComponent
) {
    val state by component.model.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)

    ) {
        TitleItem(
            iconLeftResId = R.drawable.ic_account__active,
            contentDescriptionIconLeft = R.string.icon_account_description,
            iconRightResId = R.drawable.ic_send_default,
            contentDescriptionIconRight = R.string.icon_send_description,
            colorIcon = MaterialTheme.colorScheme.onBackground,
            title = state.user.fullName,
            text = state.user.phone.convertPhone(),
            rotate = 90f
        )
        Spacer(modifier = Modifier.height(16.dp))
        TitleItem(
            iconLeftResId = R.drawable.ic_heart_default,
            iconRightResId = R.drawable.ic_right_arrow_default,
            contentDescriptionIconLeft = R.string.icon_heart_decription,
            contentDescriptionIconRight = R.string.icon_arrow_right_description,
            colorIcon = Pink,
            title = stringResource(id = R.string.favorite),
            text = state.countProduct,
            onClick = { component.onClickFavorite(state.user.id) }
        )
        LoginItem(
            iconResId = R.drawable.ic_shop_default,
            contentDescriptionId = R.string.icon_shop_description,
            colorIcon = Pink,
            titleId = R.string.shop
        )
        LoginItem(
            iconResId = R.drawable.ic_feedback_default,
            contentDescriptionId = R.string.icon_feedback_description,
            colorIcon = Orange,
            titleId = R.string.feedback
        )
        LoginItem(
            iconResId = R.drawable.ic_offer_default,
            contentDescriptionId = R.string.icon_offer_description,
            colorIcon = Grey,
            titleId = R.string.offer
        )
        LoginItem(
            iconResId = R.drawable.ic_refund_default,
            contentDescriptionId = R.string.icon_refund_description,
            colorIcon = Grey,
            titleId = R.string.refund
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(GreyLight),
            onClick = component::onClickLogOut
        ) {
            Text(text = stringResource(R.string.logout))
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun LoginItem(
    modifier: Modifier = Modifier,
    iconResId: Int,
    contentDescriptionId: Int,
    colorIcon: Color,
    titleId: Int,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .background(GreyLight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconResId),
            contentDescription = stringResource(id = contentDescriptionId),
            tint = colorIcon
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = titleId),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onClick) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_right_arrow_default
                ),
                contentDescription = stringResource(
                    R.string.icon_arrow_right_description
                )
            )
        }
    }
}

@Composable
fun TitleItem(
    modifier: Modifier = Modifier,
    iconLeftResId: Int,
    iconRightResId: Int,
    contentDescriptionIconLeft: Int,
    contentDescriptionIconRight: Int,
    colorIcon: Color,
    title: String,
    text: String,
    rotate: Float = 0f,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .background(GreyLight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconLeftResId),
            contentDescription = stringResource(id = contentDescriptionIconLeft),
            tint = colorIcon
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onClick) {
            Icon(
                modifier = Modifier
                    .rotate(rotate),
                imageVector = ImageVector.vectorResource(
                    id = iconRightResId
                ),
                contentDescription = stringResource(
                    contentDescriptionIconRight
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteItemPreview() {
    TitleItem(
        iconLeftResId = R.drawable.ic_account__active,
        contentDescriptionIconLeft = R.string.icon_account_description,
        iconRightResId = R.drawable.ic_send_default,
        contentDescriptionIconRight = R.string.icon_send_description,
        colorIcon = Pink,
        text = "+7 993 377 44 02",
        title = "Мария Иванова"
    )
}

@Preview(showBackground = true)
@Composable
fun LoginItemPreview() {
    LoginItem(
        iconResId = R.drawable.ic_shop_default,
        contentDescriptionId = R.string.icon_shop_description,
        colorIcon = Pink,
        titleId = R.string.shop
    )
}


@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    LoginContent(component = LoginComponentPreview())
}

private class LoginComponentPreview : LoginComponent {
    override val model: StateFlow<LoginStore.State> =
        MutableStateFlow(
            LoginStore.State(
                user = User.USER_DEFAULT,
                countProduct = "1 товар"
            )
        )

    override fun onClickLogOut() {

    }

    override fun onClickFavorite(userId: Long) {

    }

}