package com.koba.domain.usecase

import com.koba.domain.model.Book
import com.koba.domain.repository.InterparkRepository
import javax.inject.Inject

class GetBestSellerUseCase @Inject constructor(
    private val repository: InterparkRepository
){
    suspend operator fun invoke() : List<Book> {
        return repository.getBestSeller()
    }
}