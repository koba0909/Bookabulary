package com.koba.domain.repository

import com.koba.domain.model.Book

interface InterparkRepository {
    fun getBestSeller(): List<Book>
}
