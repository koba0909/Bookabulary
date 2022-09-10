package com.koba.domain.model

data class Book(
    val title: String,
    val description: String,
    val pubDate: String,
    val priceStandard: Int,
    val imageUrl: String,
    val categoryName: String,
    val reviewRating: Float,
    val author: String,
    val rank: Int,
    val publisher: String,
    val link: String
)
