package com.koba.data

import com.koba.data.dto.BestSellerDto
import com.koba.domain.model.Book

fun BestSellerDto.toBooks(): List<Book> {
    return items.map { 
        Book(
        title = it.title,
        description = it.description,
        pubDate = it.pubDate,
        priceStandard = it.priceStandard,
        imageUrl = it.imageUrl,
        categoryName = it.categoryName,
        reviewRating = it.reviewRating,
        author = it.author,
        rank = it.rank,
        publisher = it.publisher,
        link = it.link
        )
    }
}