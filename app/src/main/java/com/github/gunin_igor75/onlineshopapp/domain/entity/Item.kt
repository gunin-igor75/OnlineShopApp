package com.github.gunin_igor75.onlineshopapp.domain.entity

import android.os.Parcelable
import com.github.gunin_igor75.onlineshopapp.R
import kotlinx.parcelize.Parcelize


@Parcelize
data class Item(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: Price,
    val feedback: Feedback,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<Info>,
    val ingredients: String,
    val imagesId: List<Int>,
    val isFavorite: Boolean = false
) : Parcelable {
    companion object {
        val ITEM_DEFAULT = Item(
            id = "54a876a5-2205-48ba-9498-cfecff4baa6e",
            title = "A`PIEU",
            subtitle = "Пенка для умывания A`PIEU` `DEEP CLEAN` 200  мл ",
            price = Price(
                price = 899,
                discount = 39,
                priceWithDiscount = 549,
                unit = "₽"
            ),
            feedback = Feedback(
                count = 4,
                rating = 4.3f
            ),
            tags = listOf("face"),
            available = 30,
            description = "Пенка для лица Глубокое очищение содержит минеральную воду и соду, способствует глубокому очищению пор от различных загрязнений, контроллирует работу сальных желез, сужает поры. Обладает мягким антиептическим действием, не пересушивает кожу. Минеральная вода тонизирует и смягчает кожу.",
            info = listOf(
                Info(
                    title = "Артикул товара",
                    value = "133987"
                ),
                Info(
                    title = "Область использования",
                    value = "Лицо"
                ),
                Info(
                    title = "Страна производства",
                    value = "Республика Корея"
                ),
            ),
            ingredients = "Water, Glycerin Palmitic Acid, Stearic Acid, Myristic Acid, Potassium Hydroxide, Lauric Acid, Cocamidopropyl Betaine, Tea-Lauryl Sulfate, Phenoxyethanol, Sodium Chloride, Acrylates/C10-30 Alkyl Acrylate Crosspolymer, Arachidic Acid, Fragrance, Cellulose Gum, Disodium Edta, Capric Acid, Sodium Benzoate",
            imagesId = listOf(
                R.drawable.image_deep,
                R.drawable.image_coenzyme
            ),
            isFavorite = false
        )
    }
}

