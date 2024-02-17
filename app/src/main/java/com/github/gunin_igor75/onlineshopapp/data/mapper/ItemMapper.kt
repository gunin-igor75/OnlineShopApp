package com.github.gunin_igor75.onlineshopapp.data.mapper

import com.github.gunin_igor75.onlineshopapp.R
import com.github.gunin_igor75.onlineshopapp.domain.entity.Feedback
import com.github.gunin_igor75.onlineshopapp.domain.entity.Info
import com.github.gunin_igor75.onlineshopapp.domain.entity.Item
import com.github.gunin_igor75.onlineshopapp.domain.entity.Price
import com.github.gunin_igor75.onlineshopapp.utils.UIContentDto

fun List<UIContentDto.ItemDto>.toItems() = map { it.toItem() }

fun UIContentDto.ItemDto.PriceDto.toPrice(): Price {
    return Price(price, discount, priceWithDiscount, unit)
}

fun UIContentDto.ItemDto.FeedbackDto.toFeedback(): Feedback {
    return Feedback(count, rating)
}

fun UIContentDto.ItemDto.InfoDto.toInfo(): Info {
    return Info(title, value)
}


fun List<UIContentDto.ItemDto.InfoDto>.toInfo(): List<Info> {
    return this.map { it.toInfo() }
}

fun UIContentDto.ItemDto.toItem(): Item {
    return Item(
        id = id,
        title = title,
        subtitle = subtitle,
        price = price.toPrice(),
        feedback = feedback.toFeedback(),
        tags = tags,
        available = available,
        description = description,
        info = info.toInfo(),
        ingredients = ingredients,
        imagesId = mapImagesId[id] ?: throw IllegalArgumentException("Item with id $id not images")
    )
}

private val mapImagesId: Map<String, List<Int>> =
    mapOf(
        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" to listOf(
            R.drawable.image_vox,
            R.drawable.image_eveline
        ),
        "54a876a5-2205-48ba-9498-cfecff4baa6e" to listOf(
            R.drawable.image_deep,
            R.drawable.image_coenzyme
        ),
        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" to listOf(
            R.drawable.image_eveline,
            R.drawable.image_vox
        ),
        "16f88865-ae74-4b7c-9d85-b68334bb97db" to listOf(
            R.drawable.image_deco,
            R.drawable.image_nand_mask_sheet
        ),
        "26f88856-ae74-4b7c-9d85-b68334bb97db" to listOf(
            R.drawable.image_coenzyme,
            R.drawable.image_deco
        ),
        "15f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(
            R.drawable.image_vox,
            R.drawable.image_deep
        ),
        "88f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(
            R.drawable.image_nand_mask_sheet,
            R.drawable.image_deco
        ),
        "55f58865-ae74-4b7c-9d81-b78334bb97db" to listOf(
            R.drawable.image_deep,
            R.drawable.image_eveline
        ),
    )
