package com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.Feedback
import com.github.gunin_igor75.onlineshopapp.domain.entity.Info
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.entity.Price
import com.github.gunin_igor75.onlineshopapp.presentation.component.FavoriteButton
import com.github.gunin_igor75.onlineshopapp.presentation.component.RatingBarStars
import com.github.gunin_igor75.onlineshopapp.presentation.component.TextCrossed
import com.github.gunin_igor75.onlineshopapp.presentation.component.TextFill
import com.github.gunin_igor75.onlineshopapp.presentation.component.TopBarAppWithNavActionNotTitle
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Grey
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.GreyLight
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Pink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    component: DetailsComponent,
) {
    val state by component.model.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBarAppWithNavActionNotTitle(
            onClickBack = component::onClickBack,
            onClickAction = {/*TODO()**/ },
        )
        BodyContent(
            state = state,
            component = component,
        )
    }
}

@Composable
private fun BodyContent(
    modifier: Modifier = Modifier,
    state: DetailsStore.State,
    component: DetailsComponent
) {
    Column(
        modifier = modifier
            .padding(
                start = 16.dp,
                end = 4.dp,
            )
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
        SwipeItemDetails(imagesId = state.item.imagesId)
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.details_description),
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        BrandButton(
            modifier = Modifier.fillMaxWidth(),
            text = state.item.title
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextDecoration(
            text = state.item.description
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.details_characteristic),
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        InfoText(
            info = state.item.info,
            modifier = Modifier.fillMaxHeight()
        )
        Spacer(modifier = Modifier.height(8.dp))
        CompoundItem(text = state.item.ingredients)
        Spacer(modifier = Modifier.height(8.dp))
        AddBasket(
            price = state.item.price,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AddBasket(
    modifier: Modifier = Modifier,
    price: Price
) {
    val shape = RoundedCornerShape(corner = CornerSize(6.dp))
    val newPrice = "${price.priceWithDiscount} ${price.unit}"
    IconButton(
        modifier = modifier
            .clip(shape)
            .background(Pink),
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.background
        ),
        onClick = { }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = newPrice,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextCrossed(
                text = price.price.toString(),
                fontSize = 10
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.details_add_basket),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp)
            )
        }
    }

}

@Composable
fun CompoundItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.details_compound),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_copy_default),
            contentDescription = stringResource(R.string.icon_copy_description)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    TextDecoration(text = text)
}

@Composable
fun InfoText(
    modifier: Modifier = Modifier,
    info: List<Info>
) {
    Column(
        modifier = modifier
    ) {
        info.forEach { elem ->
            Row {
                Text(
                    text = elem.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = elem.value,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Grey)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun BrandButton(
    modifier: Modifier = Modifier,
    text: String
) {
    val shape = RoundedCornerShape(corner = CornerSize(6.dp))
    Box(
        modifier = modifier
            .clip(shape)
            .background(GreyLight)

    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_right_arrow_default
                ),
                contentDescription = "Button brand"
            )
        }

    }
}

@Composable
private fun TextDecoration(
    modifier: Modifier = Modifier,
    text: String
) {
    var buttonState: ButtonState
            by rememberSaveable { mutableStateOf(ButtonState.HIDE) }

    var textOverflow by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val maxLine = if (buttonState == ButtonState.HIDE) 2 else Int.MAX_VALUE
        Text(
            text = text,
            maxLines = maxLine,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            onTextLayout = { textLayoutResult ->
                textOverflow = textLayoutResult.hasVisualOverflow
            }
        )
        if (textOverflow || maxLine > 2) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        buttonState = if (buttonState == ButtonState.HIDE) {
                            ButtonState.DETAILS
                        } else {
                            ButtonState.HIDE
                        }
                    },
                text = if (buttonState == ButtonState.HIDE) {
                    stringResource(R.string.button_details)
                } else {
                    stringResource(R.string.button_hiden)
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp
                )
            )
        }
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.SwipeItemDetails(
    modifier: Modifier = Modifier,
    imagesId: List<Int>,
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
    Row(
        modifier = Modifier
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_question_default),
                contentDescription = stringResource(R.string.button_question_description),
                tint = GreyLight
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
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


@Preview(showBackground = true)
@Composable
private fun DetailsContentPreview() {
    OnlineShopAppTheme {
        DetailsContent(
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
