package com.koba.bookabulary

import android.net.Uri
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.koba.domain.model.Book
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object BookabularyDestinations {
    const val MAIN_ROUTE = "main"
    const val DETAIL_ROUTE = "detail"
}

class BookabularyActions(navController: NavHostController) {
    val navigateToDetail: (Book) -> Unit = {
        val bookString = Uri.encode(
            Json.encodeToString(it)
        )

        navController.navigate(
            "${BookabularyDestinations.DETAIL_ROUTE}/$bookString"
        ) {

            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }
}
