package com.github.gunin_igor75.onlineshopapp.presentation.main.profile.favorite

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.presentation.component.TopBarAppWithNav
import com.github.gunin_igor75.onlineshopapp.presentation.main.catalogs.catalog.CardItem
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Grey
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.GreyLight
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.OnlineShopAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val itemsTabs = listOf(R.string.title_product, R.string.title_brand)

@Composable
fun FavoriteContent(
    component: FavoriteComponent
) {
    val state by component.model.collectAsState()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topAppBar, tabs, bodyContent) = createRefs()
        TopBarAppWithNav(
            titleResId = R.string.title_favorite,
            onClickBack = component::onClickBack,
            modifier = Modifier
                .constrainAs(topAppBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        FavoriteTabs(
            modifier = Modifier
                .constrainAs(tabs) {
                    top.linkTo(topAppBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Surface(
            modifier = Modifier
                .constrainAs(bodyContent) {
                    top.linkTo(tabs.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 50.dp)
        ) {
            Crossfade(targetState = FavoriteRouter.currentScreen, label = "") { screen ->
                when (screen.value) {
                    FavoriteScreenType.Brand -> {

                    }

                    FavoriteScreenType.Products -> {
                        ProductsScreen(
                            items = state.items,
                            onClickItem = component::onClickItem,
                            onClickChangeFavorite = component::onChangeFavorite
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsScreen(
    items: List<Item>,
    onClickItem: (Item) -> Unit,
    onClickChangeFavorite: (Item) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = items,
            key ={item -> item.id }
        ){
            CardItem(
                item = it,
                onClickItem = onClickItem,
                onClickChangeFavorite = onClickChangeFavorite
            )
        }
    }
}

@Composable
private fun FavoriteTabs(
    modifier: Modifier = Modifier
) {
    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val shape = RoundedCornerShape(4.dp)
    TabRow(
        modifier = modifier
            .background(GreyLight)
            .padding(horizontal = 1.dp, vertical = 1.dp)
            .clip(shape)
            .padding(2.dp),
        selectedTabIndex = selectedIndex,
        indicator = { tabPositions: List<TabPosition> ->
            Box {}
        },
        divider = { Box { } }
    ) {
        itemsTabs.forEachIndexed { index, nameSource ->
            val selected = index == selectedIndex
            Tab(
                modifier = if (selected) {
                    Modifier
                        .clip(shape)
                        .background(Color.White)
                } else {
                    Modifier
                        .clip(shape)
                        .background(GreyLight)
                },
                selected = selected,
                onClick = {
                    selectedIndex = index
                    changeScreen(index)
                }
            ) {
                val color = if (selected) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    Grey
                }
                Text(
                    text = stringResource(id = nameSource),
                    color = color
                )
            }
        }
    }
}

private fun changeScreen(index: Int) {
    return when (index) {
        0 -> FavoriteRouter.navigateTo(FavoriteScreenType.Products)
        else -> FavoriteRouter.navigateTo(FavoriteScreenType.Brand)
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteTabsPreview() {
    OnlineShopAppTheme {
        FavoriteTabs()
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteContentPreview() {
    FavoriteContent(component = FavoriteComponentPreview())
}

private class FavoriteComponentPreview : FavoriteComponent {

    override val model: StateFlow<FavoriteStore.State> =
        MutableStateFlow(
            FavoriteStore.State(
                items = Item.ITEMS_FAVORITE
            )
        )

    override fun onClickBack() {}
    override fun onClickItem(item: Item) {
    }
    override fun onChangeFavorite(item: Item) {}
}