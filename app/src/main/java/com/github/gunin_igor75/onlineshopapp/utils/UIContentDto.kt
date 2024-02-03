package com.github.gunin_igor75.onlineshopapp.utils


data class UIContentDto(
    val items: List<ItemDto>
) {
    data class ItemDto(
        val id: String,
        val title: String,
        val subtitle: String,
        val price: PriceDto,
        val feedback: FeedbackDto,
        val tags: List<String>,
        val available: Int,
        val description: String,
        val info: List<InfoDto>,
        val ingredients: String
    ) {
        data class PriceDto(
            val price: Int,
            val discount: Int,
            val priceWithDiscount: Int,
            val unit: String
        )

        data class FeedbackDto(
            val count: Int,
            val rating: Float
        )

        data class InfoDto(
            val title: String,
            val value: String
        )
    }
}