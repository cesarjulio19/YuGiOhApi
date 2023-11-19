package com.example.yugiohapi.data.api

data class CardApiModel(
    val id: Int,
    val name: String,
    val type: String,
    val desc: String,
    val archetype: String,
    val card_images: List<CardImage>
)

data class CardListApiModel(
    val list:List<CardApiModel>
)
