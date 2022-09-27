package com.koba.presenter.main

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.koba.domain.model.Book

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val state = mainViewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    LaunchedEffect(Unit) {
        mainViewModel.effect
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect {
                when (it) {
                    is MainEffect.ShowToast -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 10.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        BestSellerGrid(
            books = state.value.bestSellers,
            cellCount = if (isPortrait) 3 else 5
        ) {
            // TODO move detail screen
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
        }
        CircularProgress(isShow = state.value.isLoading)
    }
}

@Composable
fun BestSellerGrid(books: List<Book>, cellCount: Int, onClickBook: (Book) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(cellCount),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = books
        ) { book ->
            BookItem(book, onClickBook)
        }
    }
}

@Composable
fun BookItem(book: Book, onClickBook: (Book) -> Unit) {
    Column(
        modifier = Modifier.height(190.dp)
            .clickable {
                onClickBook.invoke(book)
            }
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
