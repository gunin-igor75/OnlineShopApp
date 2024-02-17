package com.github.gunin_igor75.onlineshopapp.presentation.main.profile.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.GreyLight
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Pink

@Composable
fun LoginContent(
    component: LoginComponent
) {

}

@Composable
fun LoginItem(
    modifier: Modifier = Modifier,
    iconResId: Int,
    contentDescriptionId: Int,
    colorIcon: Color,
    title: String,
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
            text = title,
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
fun FavoriteItem(
    modifier: Modifier = Modifier,
    iconResId: Int,
    contentDescriptionId: Int,
    colorIcon: Color,
    title: String,
    phone: String,
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
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text =  phone,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onClick) {
            Icon(
                modifier = Modifier
                    .rotate(90f),
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_send_default
                ),
                contentDescription = stringResource(
                    R.string.icon_send_description
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteItemPreview() {
    FavoriteItem(
        iconResId = R.drawable.ic_account__active,
        contentDescriptionId = R.string.icon_account_description,
        colorIcon = Pink,
        phone = "+7 993 377 44 02",
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
        title = stringResource(R.string.shop)
    )
}