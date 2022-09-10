package com.koba.data.repository

import com.koba.data.dto.BestSellerDto
import com.koba.data.mock.MockData
import com.koba.domain.repository.InterparkRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MockingInterparkRepositoryImpl : InterparkRepository {
    override fun getBestSeller(): BestSellerDto {
        return Json.decodeFromString(
            MockData.bestSellerJson
        )
    }
}