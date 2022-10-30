package com.koba.bookabulary

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.koba.domain.model.Book
import com.koba.presenter.detail.DetailRoute
import com.koba.presenter.detail.DetailViewModel
import com.koba.presenter.main.MainRoute
import com.koba.presenter.navitype.BookType

@Composable
fun BookabularyNavGraph(
    navHostController: NavHostController,
    navigateToDetail: (Book) -> Unit,
    startDestination: String = BookabularyDestinations.MAIN_ROUTE
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(BookabularyDestinations.MAIN_ROUTE) {
            MainRoute(
                viewModel = hiltViewModel(),
                onNavigateToBookDetail = {
                    navigateToDetail.invoke(it)
                }
            )
        }

        composable(
            route = "${BookabularyDestinations.DETAIL_ROUTE}/{${DetailViewModel.KEY_SELECTED_BOOK}}",
            arguments = listOf(
                navArgument(DetailViewModel.KEY_SELECTED_BOOK) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            DetailRoute(viewModel = hiltViewModel())
        }
    }
}
