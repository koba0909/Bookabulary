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
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.koba.domain.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    state: State<MainState>,
    effectFlow: Flow<MainEffect>,
    onRequestRefreshBookList: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    val pageState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        "베스트 도서",
        "추천 도서",
        "신작 도서"
    )

    LaunchedEffect(Unit) {
        effectFlow
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
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TabRow(
                selectedTabIndex = pageState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.pagerTabIndicatorOffset(pageState, tabPositions)
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pageState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pageState.scrollToPage(index)
                            }
                            Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text(title)
                    }
                }
            }

            HorizontalPager(
                count = 3,
                state = pageState
            ) { page ->
                when (page) {
                    0 -> {
                        BestSellerGrid(
                            books = state.value.bestSellers,
                            cellCount = if (isPortrait) 3 else 5
                        ) {
                            // TODO move detail screen
                            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
                        }
                    }
                    1 -> {
                        BestSellerGrid(
                            books = state.value.bestSellers,
                            cellCount = if (isPortrait) 3 else 5
                        ) {
                            // TODO move detail screen
                            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
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
                    .memoryCacheKey(book.title)
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
