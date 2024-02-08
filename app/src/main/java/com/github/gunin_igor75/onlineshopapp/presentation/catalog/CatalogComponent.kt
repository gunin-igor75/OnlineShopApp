package com.github.gunin_igor75.onlineshopapp.presentation.catalog

import com.github.gunin_igor75.onlineshopapp.domain.entity.Item

interface CatalogComponent {
     fun sortFeedbackRating()
     fun sortPriceDesc()
     fun sortPriceAsc()
     fun choseFace()
     fun choseBody()
     fun choseSuntan()
     fun choseMask()
     fun choseAll()
     fun changeFavorite(item: Item)
     fun onItem(item: Item)
}