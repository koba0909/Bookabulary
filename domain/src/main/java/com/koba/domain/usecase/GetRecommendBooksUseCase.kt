package com.koba.domain.usecase

import com.koba.domain.repository.InterparkRepository
import javax.inject.Inject

class GetRecommendBooksUseCase @Inject constructor(
    private val repository: InterparkRepository
) {
    suspend operator fun invoke() = repository.getRecommendBooks()
}
