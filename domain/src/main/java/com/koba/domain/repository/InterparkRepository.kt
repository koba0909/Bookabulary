package com.koba.domain.repository

import com.koba.domain.model.Book

interface InterparkRepository {
    suspend fun getBestSellerBooks(): List<Book>
    suspend fun getRecommendBooks(): List<Book>
    suspend fun getNewBooks(): List<Book>
}
