package com.example.yugiohapi.data.api

data class CardListResponse(
    val data: List<CardListItemResponse>
)

data class CardListItemResponse(
    val name:String
)

data class CardDetailResponse(
    val id: Int,
    val name: String,
    val type: String,
    val desc: String,
    val archetype: String,
    val card_images: List<CardImage>
)

data class CardImage(
    val id: Int,
    val image_url: String,
    val image_url_small: String,
    val image_url_cropped: String
)
