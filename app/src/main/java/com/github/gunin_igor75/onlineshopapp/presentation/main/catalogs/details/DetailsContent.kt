package com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
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
            .padding(8.dp)
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
        SwipeItem(imagesId = state.item.imagesId)
    }
}

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
            contentDescription = stringResource(R.string.details_button_favorite_description)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.SwipeItem(
    modifier: Modifier = Modifier,
    imagesId: List<Int>
) {
    val pageState = rememberPagerState(
        pageCount = { imagesId.size }
    )
    HorizontalPager(state = pageState) {page ->
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
                contentDescription = stringResource(R.string.button_question_description)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
    Row(
        modifier = Modifier.padding(2.dp)
            .align(Alignment.CenterHorizontally),
        horizontalArrangement = Arrangement.Center
    ) {
        imagesId.forEachIndexed { index, _ ->
            if (pageState.currentPage == index) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_big_pagination_active),
                    contentDescription = stringResource(R.string.pagination_active_description)
                )
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_big_pagination_default),
                    contentDescription = stringResource(R.string.pagination_not_active_desription)
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
