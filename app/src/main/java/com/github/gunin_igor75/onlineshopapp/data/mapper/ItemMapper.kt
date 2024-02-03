package com.github.gunin_igor75.onlineshopapp.data.mapper

import com.github.gunin_igor75.onlineshopapp.domain.model.Feedback
import com.github.gunin_igor75.onlineshopapp.domain.model.Info
import com.github.gunin_igor75.onlineshopapp.domain.model.Item
import com.github.gunin_igor75.onlineshopapp.domain.model.Price
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
        ingredients = ingredients
    )
}