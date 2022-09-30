package com.koba.bookabulary

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.koba.presenter.main.MainRoute

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
    }
}
