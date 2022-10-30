package com.koba.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@kotlinx.serialization.Serializable
@Parcelize
data class Book(
    val title: String,
    val description: String,
    val pubDate: String,
    val priceStandard: Int,
    val imageUrl: String,
    val categoryName: String,
    val reviewRating: Float,
    val author: String,
    val publisher: String,
    val link: String
) : Parcelable
