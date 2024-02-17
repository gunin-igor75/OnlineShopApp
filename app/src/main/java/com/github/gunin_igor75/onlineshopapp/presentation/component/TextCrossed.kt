package com.github.gunin_igor75.onlineshopapp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextCrossed(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int
) {
    val color = MaterialTheme.colorScheme.onBackground
    Box {
        Text(
            text = text,
            fontSize = fontSize.sp,
            modifier = Modifier
                .padding(4.dp)
                .drawBehind {
                    drawLine(
                        start = Offset(x = -4f, y = size.height - size.height / 2.7f),
                        end = Offset(x = size.width + 4f, y = size.height - size.height / 1.7f),
                        color = color,
                        strokeWidth = 3f
                    )
                }
        )
    }
}