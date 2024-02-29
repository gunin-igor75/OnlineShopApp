package com.github.gunin_igor75.onlineshopapp.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Orange
import kotlin.math.abs

@Composable
fun RatingBarStars(
    modifier: Modifier = Modifier,
    rating: Float,
    count: Int = 5
) {
    Row(
        modifier = modifier.wrapContentSize()
    ) {
        for (index in 1..count) {
            val icon = getIcon(index, rating)
            Icon(
                modifier = Modifier.size(10.dp),
                imageVector = icon,
                contentDescription = stringResource(R.string.icon_star_rating_description),
                tint = Orange
            )
        }
    }
}

private fun getIcon(index: Int, rating: Float): ImageVector {
    val isStarHalf =  abs(rating - index) < 1 && (rating % 1) != 0.0f
    if (index <= rating) {
        return Icons.Rounded.Star
    }
    if (isStarHalf) {
        return Icons.AutoMirrored.Rounded.StarHalf
    }
    return Icons.Rounded.StarOutline
}

@Preview(showBackground = true)
@Composable
fun RatingBarStarsPreview() {
    RatingBarStars(rating = 4.3f)
}