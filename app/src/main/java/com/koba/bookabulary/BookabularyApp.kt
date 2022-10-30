package com.koba.bookabulary

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.koba.bookabulary.ui.theme.BookabularyTheme

@Composable
fun BookabularyApp() {
    BookabularyTheme {
        val navHostController = rememberNavController()
        val navigationActions = remember(navHostController) {
            BookabularyActions(navHostController)
        }

        BookabularyNavGraph(
            navHostController = navHostController,
            navigateToDetail = navigationActions.navigateToDetail
        )
    }
}
