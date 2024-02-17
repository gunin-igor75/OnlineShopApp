package com.github.gunin_igor75.onlineshopapp.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Pink

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        val iconId = if (isFavorite) {
            R.drawable.ic_heart_active
        } else {
            R.drawable.ic_heart_default
        }
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
            contentDescription = stringResource(
                R.string.details_button_favorite_description,
            ),
            tint = Pink
        )
    }
}