package com.koba.data.repository

import com.koba.data.dto.BestSellerDto
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
    override suspend fun getBestSeller(): List<Book> {
        return jsonBuilder.decodeFromString<BestSellerDto>(
            MockData.bestSellerJson
        ).toBooks()
    }
}
