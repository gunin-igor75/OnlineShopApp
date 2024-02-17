package com.github.gunin_igor75.onlineshopapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Pink

@Composable
fun TextFill(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int,
) {
    val shape = RoundedCornerShape(corner = CornerSize(4.dp))
    Box(
        modifier = modifier
            .clip(shape)
            .background(Pink)
            .padding(vertical = 1.dp, horizontal = 4.dp)
    ) {
        Text(
            modifier = Modifier,
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSize.sp),
            color = MaterialTheme.colorScheme.background
        )
    }
}