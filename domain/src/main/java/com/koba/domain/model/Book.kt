package com.koba.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@kotlinx.serialization.Serializable
@Parcelize
data class Book(
    val title: String = "",
    val description: String = "",
    val pubDate: String = "",
    val priceStandard: Int = 0,
    val imageUrl: String = "",
    val categoryName: String = "",
    val reviewRating: Float = 0f,
    val author: String = "",
    val publisher: String = "",
    val link: String = ""
) : Parcelable
