package com.koba.data.repository

import com.koba.data.dto.InterparkBookDto
import com.koba.data.mock.MockData
import com.koba.data.toBooks
import com.koba.domain.model.Book
import com.koba.domain.repository.InterparkRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MockingInterparkRepositoryImpl : InterparkRepository {
    val jsonBuilder = Json {
        ignoreUnknownKeys = true
    }
    override suspend fun getBestSellerBooks(): List<Book> {
        return jsonBuilder.decodeFromString<InterparkBookDto>(
            MockData.bestSellerJson
        ).toBooks()
    }

    override suspend fun getRecommendBooks(): List<Book> {
        return jsonBuilder.decodeFromString<InterparkBookDto>(
            MockData.bestSellerJson
        ).toBooks()
    }

    override suspend fun getNewBooks(): List<Book> {
        return jsonBuilder.decodeFromString<InterparkBookDto>(
            MockData.bestSellerJson
        ).toBooks()
    }
}
