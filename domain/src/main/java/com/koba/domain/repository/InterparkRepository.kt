package com.koba.domain.repository

import com.koba.domain.model.Book

interface InterparkRepository {
    suspend fun getBestSeller(): List<Book>
}