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

        val ITEMS_FAVORITE = listOf<Item>(
            Item(
                id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
                title = "ESFOLIO",
                subtitle = "Лосьон для тела `ESFOLIO` COENZYME Q10 Увлажняющий 500 мл",
                price = Price(
                    price = 749,
                    discount = 35,
                    priceWithDiscount = 489,
                    unit = "₽"
                ),
                feedback = Feedback(
                    count = 51,
                    rating = 4.5f
                ),
                tags = listOf("body"),
                available = 100,
                description = "Пенка для лица Глубокое очищение содержит минеральную воду и соду, способствует глубокому очищению пор от различных загрязнений, контроллирует работу сальных желез, сужает поры. Обладает мягким антиептическим действием, не пересушивает кожу. Минеральная вода тонизирует и смягчает кожу.",
                info = listOf(
                    Info(
                        title = "Артикул товара",
                        value = "441187"
                    ),
                    Info(
                        title = "Область использования",
                        value = "Тело"
                    ),
                    Info(
                        title = "Страна производства",
                        value = "Франция"
                    ),
                ),
                ingredients = "Glycerin Palmitic Acid, Stearic Acid, Capric Acid, Sodium Benzoate",
                imagesId = listOf(
                    R.drawable.image_vox,
                    R.drawable.image_eveline
                ),
                isFavorite = true
            ),
            Item(
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
            ),
            Item(
                id = "75c84407-52e1-4cce-a73a-ff2d3ac031b3",
                title = "DECO.",
                subtitle = "Салфетки для лица `DECO.` матирующие с экстрактом зеленого чая 100 шт",
                price = Price(
                    price = 329,
                    discount = 40,
                    priceWithDiscount = 199,
                    unit = "₽"
                ),
                feedback = Feedback(
                    count = 15,
                    rating = 4.0f
                ),
                tags = listOf("face"),
                available = 22,
                description = "Салфетки для лица `DECO.` матирующие с экстрактом зеленого чая содержит минеральную воду и соду, способствует глубокому очищению пор от различных загрязнений, контроллирует работу сальных желез, сужает поры. Обладает мягким антиептическим действием, не пересушивает кожу. Минеральная вода тонизирует и смягчает кожу.",
                info = listOf(
                    Info(
                        title = "Артикул товара",
                        value = "324567"
                    ),
                    Info(
                        title = "Область использования",
                        value = "Лицо"
                    ),
                    Info(
                        title = "Страна производства",
                        value = "Республика Конго"
                    ),
                ),
                ingredients = "Myristic Acid, Potassium Hydroxide, Lauric Acid, Cocamidopropyl Betaine, Tea-Lauryl Sulfate, Phenoxyethanol, Sodium Chloride, Acrylates/C10-30 Alkyl Acrylate Crosspolymer, Arachidic Acid, Fragrance, Cellulose Gum, Disodium Edta, Capric Acid, Sodium Benzoate",
                imagesId = listOf(
                    R.drawable.image_eveline,
                    R.drawable.image_vox
                ),
                isFavorite = true
            ),
            Item(
                id = "16f88865-ae74-4b7c-9d85-b68334bb97db",
                title = "LP CARE",
                subtitle = "Маска-перчатки для рук `LP-CARE` увлажняющая 40 мл",
                price = Price(
                    price = 169,
                    discount = 42,
                    priceWithDiscount = 99,
                    unit = "₽"
                ),
                feedback = Feedback(
                    count = 140,
                    rating = 4.6f
                ),
                tags = listOf(
                    "mask",
                    "body"
                ),
                available = 51,
                description = "Маска-перчатки для рук `LP-CARE` увлажняющая способствует глубокому очищению пор от различных загрязнений",
                info = listOf(
                    Info(
                        title = "Артикул товара",
                        value = "558899"
                    ),
                    Info(
                        title = "Область использования",
                        value = "Тело"
                    ),
                    Info(
                        title = "Страна производства",
                        value = "Республика Корея"
                    ),
                ),
                ingredients = "Water, Glycerin Palmitic Acid, Cocamidopropyl Betaine, Tea-Lauryl Sulfate, Phenoxyethanol, Sodium Chloride, Acrylates/C10-30 Alkyl Acrylate Crosspolymer, Arachidic Acid, Fragrance, Cellulose Gum, Disodium Edta, Capric Acid, Sodium Benzoate",
                imagesId = listOf(
                    R.drawable.image_deco,
                    R.drawable.image_nand_mask_sheet
                ),
                isFavorite = true
            ),
            Item(
                id = "26f88856-ae74-4b7c-9d85-b68334bb97db",
                title = "EVO",
                subtitle = "Молочко для тела Пантенол 250 мл",
                price = Price(
                    price = 299,
                    discount = 20,
                    priceWithDiscount = 239,
                    unit = "₽"
                ),
                feedback = Feedback(
                    count = 99,
                    rating = 3.2f
                ),
                tags = listOf("body"),
                available = 73,
                description = "Увлажняющее молочко с 2%-ным содержанием Декспантенола предназначено для ежедневного ухода за кожей, в том числе за очень сухой и склонной к шелушению. При ежедневном применении идеально увлажненная кожа остается нежной, гладкой и бархатистой в течение всего дня.",
                info = listOf(
                    Info(
                        title = "Артикул товара",
                        value = "111899"
                    ),
                    Info(
                        title = "Область использования",
                        value = "Тело"
                    ),
                ),
                ingredients = "Water, Sorbitol, Isopropyl Palmitate, Panthenol, Cetearyl Alcohol (and) Potassium Cetyl Phosphate",
                imagesId = listOf(
                    R.drawable.image_coenzyme,
                    R.drawable.image_deco
                ),
                isFavorite = true
            ),
            Item(
                id = "15f88865-ae74-4b7c-9d81-b78334bb97db",
                title = "Aravia Professional",
                subtitle = "Маска с поросуживающим эффектом Aravia Professional Post-Acne Balance Mask 100 мл",
                price = Price(
                    price = 199,
                    discount = 50,
                    priceWithDiscount = 99,
                    unit = "₽"
                ),
                feedback = Feedback(
                    count = 111,
                    rating = 3.8f
                ),
                tags = listOf(
                    "mask",
                    "face"
                ),
                available = 14,
                description = "Маска Aravia Professional увлажняющая способствует глубокому очищению пор от различных загрязнений",
                info = listOf(
                    Info(
                        title = "Артикул товара",
                        value = "158811"
                    ),
                    Info(
                        title = "Область использования",
                        value = "Лицо"
                    ),
                    Info(
                        title = "Страна производства",
                        value = "Республика Узбекистан"
                    ),
                ),
                ingredients = "Cocamidopropyl Betaine, Tea-Lauryl Sulfate, Phenoxyethanol, Sodium Chloride, Acrylates/C10-30 Alkyl Acrylate Crosspolymer, Arachidic Acid, Fragrance, Cellulose Gum, Disodium Edta, Capric Acid, Sodium Benzoate",
                imagesId = listOf(
                    R.drawable.image_vox,
                    R.drawable.image_deep
                ),
                isFavorite = true
            ),
            Item(
                id = "88f88865-ae74-4b7c-9d81-b78334bb97db",
                title = "Floresan",
                subtitle = "Масло водостойкое Floresan Активатор загара SPF 20 150 мл",
                price = Price(
                    price = 499,
                    discount = 50,
                    priceWithDiscount = 199,
                    unit = "₽"
                ),
                feedback = Feedback(
                    count = 16,
                    rating = 4.8f
                ),
                tags = listOf("suntan"),
                available = 14,
                description = "Масло водостойкое Активатор загара SPF 20",
                info = listOf(
                    Info(
                        title = "Артикул товара",
                        value = "333811"
                    ),
                    Info(
                        title = "Страна производства",
                        value = "Республика Узбекистан"
                    ),
                ),
                ingredients = "вода питьевая; масло парфюмерное; масло соевое; циклометикон; этилгексил метоксициннамат; изопропил пальмитат; экстракт моркови; масло Ши; пропиленгликоль; витамин Е; натрия хлорид; гидантоин; ?-каротин; Д-пантенол; парфюмерная композиция; лимонен; амилциннамал",
                imagesId = listOf(
                    R.drawable.image_nand_mask_sheet,
                    R.drawable.image_deco
                ),
                isFavorite = true
            ),
            Item(
                id = "55f58865-ae74-4b7c-9d81-b78334bb97db",
                title = "Floresan",
                subtitle = "Масло для загара Floresan Гавайское",
                price = Price(
                    price = 99,
                    discount = 50,
                    priceWithDiscount = 49,
                    unit = "₽"
                ),
                feedback = Feedback(
                    count = 2,
                    rating = 4.9f
                ),
                tags = listOf("suntan"),
                available = 44,
                description = "От морщин; смягчающий; усиливающий загар",
                info = listOf(
                    Info(
                        title = "Артикул товара",
                        value = "158811"
                    ),
                    Info(
                        title = "Страна производства",
                        value = "Республика Узбекистан"
                    ),
                ),
                ingredients = "Glycine Soja Oil (масло соевое), Prunus Amygdalus Dulcis Oil (Масло миндальное), Cocos Nucifera Oil (Масло кокосовое), Tocopheryl Acetate (Витамин Е), Butyrospermum Parkii Oil (Масло Ши)",
                imagesId = listOf(
                    R.drawable.image_deep,
                    R.drawable.image_eveline
                ),
                isFavorite = true
            ),
        )
    }
}

