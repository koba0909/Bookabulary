package com.koba.presenter.navitype

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.koba.domain.model.Book
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BookType : NavType<Book>(true) {
    override fun get(bundle: Bundle, key: String): Book? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Book::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): Book {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: Book) {
        bundle.putParcelable(key, value)
    }
}
