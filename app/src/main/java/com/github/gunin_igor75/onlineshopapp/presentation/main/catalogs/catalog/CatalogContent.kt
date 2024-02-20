package com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.presentation.component.ProductsComponent
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Grey
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.GreyLight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

typealias OnClick = () -> Unit


@Composable
fun CatalogContent(
    modifier: Modifier = Modifier,
    component: CatalogComponent
) {
    val state by component.model.collectAsState()

    val tags: List<Tag> = listOf(
        Tag(1, R.string.all, component::choseAll),
        Tag(2, R.string.face, component::choseFace),
        Tag(3, R.string.body, component::choseBody),
        Tag(4, R.string.suntan, component::choseSuntan),
        Tag(5, R.string.mask, component::choseMask)
    )
    val spinners: List<Tag> = listOf(
        Tag(0, R.string.sort_rating, component::sortFeedbackRating),
        Tag(2, R.string.sort_priceDesc, component::sortPriceDesc),
        Tag(3, R.string.sort_priceAsc, component::sortPriceAsc),
    )
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val (sortSpinner, filterSpinner, carouselTag, bodyContent) = createRefs()
        SortSpinner(
            tags = spinners,
            modifier = modifier
                .constrainAs(sortSpinner) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        FilterSpinner(
            modifier = modifier
                .constrainAs(filterSpinner) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )
        CarouselTag(
            tags = tags,
            modifier = modifier
                .constrainAs(carouselTag) {
                    top.linkTo(sortSpinner.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        ProductsComponent(
            items = state.items,
            onClickItem = component::onItem,
            onClickChangeFavorite = component::changeFavorite,
            modifier = modifier
                .constrainAs(bodyContent){
                    top.linkTo(carouselTag.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
private fun SortSpinner(
    modifier: Modifier = Modifier,
    tags: List<Tag>
) {
    val expanded = rememberSaveable {
        mutableStateOf(false)
    }
    val currentValue = rememberSaveable {
        mutableStateOf(tags[0].stringResId)
    }

    Box(modifier = modifier.wrapContentWidth()) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    expanded.value = !expanded.value
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_sort_by_default
                ),
                contentDescription = stringResource(
                    id = R.string.Ic_sort_by_description
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = stringResource(id = currentValue.value))
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_down_arrow_default
                ),
                contentDescription = stringResource(
                    id = R.string.icon_arrow_down_description
                )
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                }
            ) {
                tags.forEach {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = it.stringResId)) },
                        onClick = it.onclick
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterSpinner(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.wrapContentWidth()) {
        Row(
            modifier = Modifier
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_filter_default
                ),
                contentDescription = stringResource(
                    id = R.string.Ic_filter_description
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.filters)
            )
        }
    }
}

@Composable
private fun CarouselTag(
    modifier: Modifier = Modifier,
    tags: List<Tag>
) {
    var selectedIndex = rememberSaveable {
        mutableStateOf(1)
    }

    LazyRow(
        modifier = modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = tags,
            key = { it.index }
        ) {
            val selected = selectedIndex.value == it.index
            ButtonTag(
                tag = it,
                state = selectedIndex,
                selected = selected
            )
        }
    }
}

@Composable
private fun ButtonTag(
    modifier: Modifier = Modifier,
    tag: Tag,
    state: MutableState<Int>,
    selected: Boolean
) {
    val contentColor = if (selected) MaterialTheme.colorScheme.background else Grey
    val containerColor = if (selected) Grey else GreyLight
    val shape = RoundedCornerShape(corner = CornerSize(12.dp))
    Box(
        modifier = modifier
            .clip(shape)
            .background(containerColor)
            .padding(4.dp)
            .clickable {
                state.value = tag.index
                tag.onclick
            },

        ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            ) {
            Text(
                text = stringResource(id = tag.stringResId),
                color = contentColor
            )
            Icon(
                modifier = Modifier
                    .clickable { state.value = 0 },
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_small_close_default
                ),
                contentDescription = stringResource(
                    id = R.string.button_clear_value_description
                ),
                tint = contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonTagPreview() {
    ButtonTag(
        tag = Tag(0, R.string.all, {}),
        state = mutableStateOf(0),
        selected = false
    )
}

@Preview(showBackground = true)
@Composable
private fun CarouselTagPreview() {
    CarouselTag(
        tags = listOf(
            Tag(1, R.string.all, {}),
            Tag(2, R.string.face, {}),
            Tag(3, R.string.body, {}),
            Tag(4, R.string.suntan, {}),
            Tag(5, R.string.mask, {})
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SortComponentPreview() {
    SortSpinner(
        tags = listOf(
            Tag(0, R.string.sort_rating, {}),
            Tag(2, R.string.sort_priceDesc, {}),
            Tag(3, R.string.sort_priceAsc, {}),
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun FilterSpinnerPreview(
) {
    FilterSpinner()
}

@Preview(showBackground = true)
@Composable
fun CatalogContentPreview() {
    CatalogContent(component = CatalogComponentPreview())
}

private class CatalogComponentPreview : CatalogComponent {
    override val model: StateFlow<CatalogStore.State> =
        MutableStateFlow(
            CatalogStore.State(
                items = Item.ITEMS_FAVORITE
            )
        )
    override fun sortFeedbackRating() {}
    override fun sortPriceDesc() {}
    override fun sortPriceAsc() {}
    override fun choseFace() {}
    override fun choseBody() {}
    override fun choseSuntan() {}
    override fun choseMask() {}
    override fun choseAll() {}
    override fun changeFavorite(item: Item) {}
    override fun onItem(item: Item) {}
}

private data class Tag(
    val index: Int,
    val stringResId: Int,
    val onclick: OnClick
)