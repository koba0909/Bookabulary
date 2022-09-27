package com.koba.presenter.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.koba.domain.model.Book

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val state = mainViewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(
                horizontal = 10.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        BestSellerGrid(books = state.value.bestSellers)
        CircularProgress(isShow = state.value.isLoading)
    }
}

@Composable
fun BestSellerGrid(books: List<Book>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = books
        ) { book ->
            BookItem(book)
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Column(
        modifier = Modifier.height(190.dp)
    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillBounds,
                contentDescription = book.title
            )
        }
    }
}

@Composable
fun CircularProgress(isShow: Boolean) {
    if (isShow) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp)
        )
    }
}
