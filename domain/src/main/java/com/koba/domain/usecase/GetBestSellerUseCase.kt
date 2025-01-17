package com.koba.domain.usecase

import com.koba.domain.repository.InterparkRepository
import javax.inject.Inject

class GetBestSellerUseCase @Inject constructor(
    private val repository: InterparkRepository
) {
    suspend operator fun invoke() = repository.getBestSellerBooks()
}
