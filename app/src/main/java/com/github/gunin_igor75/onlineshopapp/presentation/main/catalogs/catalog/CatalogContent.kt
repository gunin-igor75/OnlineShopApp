package com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.catalog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.Feedback
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.presentation.component.FavoriteButton
import com.github.gunin_igor75.onlineshopapp.presentation.component.TextCrossed
import com.github.gunin_igor75.onlineshopapp.presentation.component.TextFill
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Grey
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Orange
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Pink

@Composable
fun CatalogContent(
    component: CatalogComponent
) {

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    item: Item = Item.ITEM_DEFAULT,
    onClickItem: (Item) -> Unit = {},
    onClickChangeFavorite: (Item) -> Unit = {}
) {

    Card(
        modifier = modifier
            .height(290.dp)
            .width(170.dp)
            .padding(
                horizontal = 4.dp,
                vertical = 2.dp
            )
            .clickable {
                onClickItem(item)
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    )
    {
        Box {
            FavoriteButton(
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.TopEnd),
                isFavorite = item.isFavorite,
                onClick = { onClickChangeFavorite(item) }
            )
            SwipeItemCatalog(imagesId = item.imagesId)
        }

        val oldPrice = "${item.price.price} ${item.price.unit}"
        TextCrossed(
            text = oldPrice,
            fontSize = 9
        )
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.price.priceWithDiscount.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = item.price.unit,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            val discount = "${item.price.discount}%"
            TextFill(
                text = discount,
                fontSize = 8
            )
        }
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.subtitle,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 10.sp),
            lineHeight = 12.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        RatingInfo(feedback = item.feedback)
        ButtonAddBasket(
            modifier = Modifier
                .align(Alignment.End)
        )
    }
}

@Composable
private fun RatingInfo(
    modifier: Modifier = Modifier,
    feedback: Feedback
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(10.dp),
            imageVector = Icons.Rounded.Star,
            contentDescription = stringResource(id = R.string.icon_star_rating_description),
            tint = Orange
        )
        Text(text = getTextRating(feedback = feedback))
    }
}

@Composable
private fun getTextRating(feedback: Feedback): AnnotatedString = buildAnnotatedString {
    withStyle(SpanStyle(fontSize = 9.sp)) {
        withStyle(
            SpanStyle(color = Orange)
        ) {
            append(feedback.rating.toString())
            append(" ")
        }
        append("(${feedback.count})")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeItemCatalog(
    modifier: Modifier = Modifier,
    imagesId: List<Int>,
) {
    val pageState = rememberPagerState(
        pageCount = { imagesId.size }
    )
    Box {
        HorizontalPager(state = pageState) { page ->
            Image(
                modifier = Modifier
                    .width(170.dp)
                    .height(130.dp),
                painter = painterResource(id = imagesId[page]),
                contentDescription = stringResource(R.string.image_item_description)
            )
        }
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(2.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            imagesId.forEachIndexed { index, _ ->
                if (pageState.currentPage == index) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.elements_
                        ),
                        contentDescription = stringResource(
                            R.string.pagination_active_description
                        ),
                        tint = Pink
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.ic_big_pagination_default
                        ),
                        contentDescription = stringResource(
                            R.string.pagination_not_active_desription
                        ),
                        tint = Grey
                    )
                }
            }
        }
    }

}

@Composable
private fun ButtonAddBasket(
    modifier: Modifier = Modifier,
    onclick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(
        topStart = 8.dp,
        bottomEnd = 8.dp
    )
    Box(
        modifier = modifier
            .clickable { onclick() }
            .clip(shape)
            .size(32.dp)
            .background(Pink)
            .drawBehind {
                drawLine(
                    start = Offset(x = center.x - 8.dp.toPx(), y = center.y),
                    end = Offset(x = center.x + 8.dp.toPx(), y = center.y),
                    color = Color.White,
                    strokeWidth = 2f
                )
                drawLine(
                    start = Offset(x = center.x, y = center.y - 8.dp.toPx()),
                    end = Offset(x = center.x, y = center.y + 8.dp.toPx()),
                    color = Color.White,
                    strokeWidth = 2f
                )
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun ButtonAddBasketPreview(
    modifier: Modifier = Modifier
) {
    ButtonAddBasket()
}

@Preview(showBackground = true)
@Composable
private fun CardItemPreview() {
    OnlineShopAppTheme {
        CardItem(
            item = Item.ITEM_DEFAULT,
            onClickChangeFavorite = {}
        )
    }
}