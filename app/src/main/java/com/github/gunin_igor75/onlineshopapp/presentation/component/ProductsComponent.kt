package com.github.gunin_igor75.onlineshopapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item

@Composable
fun ProductsComponent(
    modifier: Modifier = Modifier,
    items: List<Item>,
    onClickItem: (Item) -> Unit,
    onClickChangeFavorite: (Item) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
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