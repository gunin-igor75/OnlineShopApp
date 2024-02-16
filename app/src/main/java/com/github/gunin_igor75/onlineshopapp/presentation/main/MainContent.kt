package com.github.gunin_igor75.onlineshopapp.presentation.main

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.presentation.component.TopBarApp
import com.github.gunin_igor75.onlineshopapp.presentation.main.basket.BasketContent
import com.github.gunin_igor75.onlineshopapp.presentation.main.home.HomeContent
import com.github.gunin_igor75.onlineshopapp.presentation.main.stock.StockContent
import com.github.gunin_igor75.onlineshopapp.presentation.ui.theme.Red


@Composable
fun MainContent(
    component: MainComponent
) {
    val stateScreen = component.screenState.subscribeAsState()

    Crossfade(targetState = stateScreen, label = "") { screen ->
        Scaffold(
            topBar = { TopBarApp(titleResId = screen.value.titleResId) },
            bottomBar = {
                BottomNavigationComponent(
                    screenState = stateScreen,
                    onClickTab = component::onClickNavigation
                )
            }
        ) { paddingValues ->
            Children(stack = component.childStack) {
                when (val instance = it.instance) {
                    is MainComponent.Child.BasketChild -> {
                        BasketContent(
                            paddingValues = paddingValues,
                            component = instance.basketComponent
                        )
                    }

                    is MainComponent.Child.CatalogDetailsChild -> {

                    }

                    is MainComponent.Child.HomeChild -> {
                        HomeContent(
                            paddingValues = paddingValues,
                            component = instance.homeComponent
                        )
                    }

                    is MainComponent.Child.LoginFavoriteDetailsChild -> {

                    }

                    is MainComponent.Child.StockChild -> {
                        StockContent(
                            paddingValues = paddingValues,
                            component = instance.stockComponent
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun BottomNavigationComponent(
    modifier: Modifier = Modifier,
    screenState: State<Screen>,
    onClickTab: (Screen) -> Unit
) {
    val indexDefault = getIndexItemByScreen(screenState.value)
    var selectedItem by rememberSaveable { mutableStateOf(indexDefault) }

    NavigationBar {
        itemsScreen.forEach { item ->
            val isSelected = selectedItem == item.index
            val colorLabel = if (isSelected) Red else MaterialTheme.colorScheme.onBackground
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedItem = item.index
                    onClickTab(item.screen)
                },
                icon = {
                    val vectorResId = if (isSelected) item.iconActive else item.iconDefault
                    Icon(
                        imageVector = ImageVector.vectorResource(id = vectorResId),
                        contentDescription = stringResource(id = item.contentDescriptionResourceId)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.screen.titleResId),
                        color = colorLabel,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp)
                    )
                }
            )
        }
    }
}

private fun getIndexItemByScreen(screen: Screen): Int =
    if (screen == Screen.Home) 0 else 1

private data class NavigationItem(
    val index: Int,
    val iconDefault: Int,
    val iconActive: Int,
    val contentDescriptionResourceId: Int,
    val screen: Screen
)

private val itemsScreen = listOf(
    NavigationItem(
        0,
        R.drawable.ic_home_default,
        R.drawable.ic_home__active,
        R.string.Ic_home_description,
        Screen.Home
    ),
    NavigationItem(
        1,
        R.drawable.ic_catalog_default,
        R.drawable.ic_catalog__active,
        R.string.Ic_catalog_description,
        Screen.Catalog
    ),
    NavigationItem(
        2,
        R.drawable.ic_bag_default,
        R.drawable.ic_bag__active,
        R.string.Ic_bag_description,
        Screen.Basket
    ),
    NavigationItem(
        3,
        R.drawable.ic_discount_default,
        R.drawable.ic_discount__active,
        R.string.Ic_discount_description,
        Screen.Stock
    ),
    NavigationItem(
        4,
        R.drawable.ic_account_default,
        R.drawable.ic_account__active,
        R.string.Ic_account_description,
        Screen.Login
    )
)