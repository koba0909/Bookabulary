package com.koba.domain.usecase

import com.koba.domain.repository.InterparkRepository
import javax.inject.Inject

class GetNewBooksUseCase @Inject constructor(
    private val repository: InterparkRepository
) {
    suspend operator fun invoke() = repository.getNewBooks()
}