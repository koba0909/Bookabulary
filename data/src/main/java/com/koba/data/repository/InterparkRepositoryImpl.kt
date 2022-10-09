package com.koba.data.repository

import com.koba.data.service.InterparkService
import com.koba.data.toBooks
import com.koba.domain.model.Book
import com.koba.domain.repository.InterparkRepository
import javax.inject.Inject

class InterparkRepositoryImpl @Inject constructor(
    private val service: InterparkService
) : InterparkRepository {
    override suspend fun getBestSellerBooks(): List<Book> {
        return service.getBestSeller()
            .toBooks()
    }

    override suspend fun getRecommendBooks(): List<Book> {
        return service.getRecommend()
            .toBooks()
    }

    override suspend fun getNewBooks(): List<Book> {
        return service.getNewBook()
            .toBooks()
    }
}
