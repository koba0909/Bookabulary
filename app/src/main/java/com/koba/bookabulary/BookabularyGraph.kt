package com.koba.bookabulary

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.koba.presenter.detail.DetailRoute
import com.koba.presenter.detail.DetailViewModel
import com.koba.presenter.main.MainRoute
import com.koba.presenter.navitype.BookType

@Composable
fun BookabularyNavGraph(
    navHostController: NavHostController,
    startDestination: String = BookabularyNavigation.MAIN_ROUTE
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(BookabularyNavigation.MAIN_ROUTE) {
            MainRoute(viewModel = hiltViewModel())
        }

        composable(
            BookabularyNavigation.DETAIL_ROUTE,
            arguments = listOf(
                navArgument(DetailViewModel.KEY_SELECTED_BOOK) {
                    type = BookType()
                }
            )
        ) {
            DetailRoute(viewModel = hiltViewModel())
        }
    }
}
