package com.koba.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 추후 필요한 항목이 있을시 추가 예정
 */

@Keep
@Serializable
data class BestSellerDto(
    @SerialName("item")
    val items: List<ItemDetail>
)

@Keep
@Serializable
data class ItemDetail(
    val title: String,
    val description: String,
    val pubDate: String,
    val priceStandard: Int,
    @SerialName("coverLargeUrl")
    val imageUrl: String,
    val categoryName: String,
    @SerialName("customerReviewRank")
    val reviewRating: Float,
    val author: String,
    val rank: Int,
    val publisher: String,
    val link: String
)
