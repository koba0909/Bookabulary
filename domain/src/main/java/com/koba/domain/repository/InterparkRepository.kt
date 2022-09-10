package com.koba.domain.repository

import com.koba.data.dto.BestSellerDto

interface InterparkRepository {
    fun getBestSeller(): BestSellerDto
}
