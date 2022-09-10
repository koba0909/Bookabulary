package com.koba.data.repository

import com.koba.data.service.InterparkService
import com.koba.data.toBooks
import com.koba.domain.model.Book
import com.koba.domain.repository.InterparkRepository

class InterparkRepositoryImpl(
    private val service: InterparkService
) : InterparkRepository {
    override suspend fun getBestSeller(): List<Book> {
        return service.getBestSeller()
            .toBooks()
    }
}
