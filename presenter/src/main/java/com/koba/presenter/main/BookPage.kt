package com.koba.presenter.main

import androidx.annotation.StringRes
import com.koba.presenter.R

enum class BookPage(
    @StringRes val resId: Int
) {
    BestSeller(R.string.book_best_seller),
    Recommend(R.string.book_recommend),
    New(R.string.book_new)
}
