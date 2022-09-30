package com.koba.bookabulary

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.koba.bookabulary.ui.theme.BookabularyTheme

@Composable
fun BookabularyApp() {
    BookabularyTheme {
        val navHostController = rememberNavController()

        BookabularyNavGraph(
            navHostController = navHostController
        )
    }
}
