package com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.Feedback
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.entity.Price
import com.github.gunin_igor75.onlineshopapp.presentation.component.RatingBarStars
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Red
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DetailsContent(
    component: DetailsComponent,
    modifier: Modifier = Modifier
) {
    val state by component.model.collectAsState()

    Column(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
        ) {
            Spacer(modifier = Modifier.weight(1f))
            FavoriteButton(
                isFavorite = state.isFavorite,
                onClick = component::onClickChangeFavorite
            )
        }
        SwipeItem(
            imagesId = state.item.imagesId,
            block = { Question() }
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = state.item.title,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = state.item.subtitle,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = pluralStringResource(
                id = R.plurals.product_count,
                count = state.item.available,
                state.item.available
            ),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        RatingFeedback(feedback = state.item.feedback)
        Spacer(modifier = Modifier.height(8.dp))
        RowPrice(price = state.item.price)
    }
}

@Composable
fun RowPrice(
    modifier: Modifier = Modifier,
    price: Price
) {
    val interval = 4.dp
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = price.priceWithDiscount.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.width(interval))
        Text(
            text = price.unit,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.width(interval))
        val oldPrice = "${price.price} ${price.unit}"
        TextCrossed(
            text = oldPrice,
            fontSize = 12
        )
        Spacer(modifier = Modifier.width(interval))
        val discount = "${price.discount}%"
        TextFill(
            text = discount,
            fontSize = 8
        )
    }
}

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
            .background(Red)
            .padding(vertical = 1.dp, horizontal = 4.dp)
    ){
        Text(
            modifier = Modifier,
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSize.sp),
            color = MaterialTheme.colorScheme.background
        )
    }

}
@Composable
private fun RatingFeedback(
    modifier: Modifier = Modifier,
    feedback: Feedback
) {
    val interval = 4.dp
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingBarStars(
            rating = feedback.rating
        )
        Spacer(modifier = Modifier.width(interval))
        Text(
            text = feedback.rating.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp)
        )
        Spacer(modifier = Modifier.width(interval))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_elipse),
            contentDescription = stringResource(R.string.icon_point_description)
        )
        Spacer(modifier = Modifier.width(interval))
        Text(
            text = pluralStringResource(
                id = R.plurals.product_feedback,
                count = feedback.count,
                feedback.count,
            ),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp
            )
        )
    }

}

@Composable
private fun FavoriteButton(
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
                R.string.details_button_favorite_description
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.SwipeItem(
    modifier: Modifier = Modifier,
    imagesId: List<Int>,
    block: @Composable () -> Unit = {}
) {
    val pageState = rememberPagerState(
        pageCount = { imagesId.size }
    )
    HorizontalPager(state = pageState) { page ->
        Image(
            modifier = Modifier,
            painter = painterResource(id = imagesId[page]),
            contentDescription = stringResource(R.string.image_item_description)
        )
    }
    block()
    Row(
        modifier = Modifier
            .padding(2.dp)
            .align(Alignment.CenterHorizontally),
        horizontalArrangement = Arrangement.Center
    ) {
        imagesId.forEachIndexed { index, _ ->
            if (pageState.currentPage == index) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_big_pagination_active
                    ),
                    contentDescription = stringResource(
                        R.string.pagination_active_description
                    )
                )
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_big_pagination_default
                    ),
                    contentDescription = stringResource(
                        R.string.pagination_not_active_desription
                    )
                )
            }
        }
    }
}

@Composable
private fun Question(
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_question_default),
                contentDescription = stringResource(R.string.button_question_description)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsContentPreview() {
    OnlineShopAppTheme {
        DetailsContent(
            modifier = Modifier.fillMaxSize(),
            component = DetailsComponentPreview()
        )
    }
}

private class DetailsComponentPreview : DetailsComponent {
    override val model: StateFlow<DetailsStore.State> =
        MutableStateFlow(
            DetailsStore.State(
                item = Item.ITEM_DEFAULT,
                isFavorite = false
            )
        )

    override fun onClickBack() {}
    override fun onClickChangeFavorite() {}
}
