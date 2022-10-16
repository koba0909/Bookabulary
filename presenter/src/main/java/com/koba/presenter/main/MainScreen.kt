package com.koba.presenter.main

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.koba.domain.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    state: State<MainState>,
    effectFlow: Flow<MainEffect>,
    onRequestRefreshBookList: () -> Unit,
    onRequestRecommendBookList: () -> Unit,
    onRequestNewBookList: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cellCount = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 5
    val pageState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = BookPage.values().map { stringResource(id = it.resId) }

    LaunchedEffect(Unit) {
        launch {
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

        launch {
            snapshotFlow { pageState.currentPage }
                .collect {
                    when (pageState.currentPage) {
                        BookPage.Recommend.ordinal -> {
                            if (!state.value.isLoadingRecommend && state.value.recommendBooks.isEmpty()) {
                                onRequestRecommendBookList.invoke()
                            }
                        }
                        BookPage.New.ordinal -> {
                            if (!state.value.isLoadingNew && state.value.newBooks.isEmpty()) {
                                onRequestNewBookList.invoke()
                            }
                        }
                    }
                }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        BookTabRow(
            coroutineScope = coroutineScope,
            pageState = pageState,
            titles = tabs
        )

        BookHorizontalPager(
            modifier = Modifier.padding(horizontal = 10.dp),
            pageState = pageState,
            state = state,
            cellCount = cellCount,
            onRequestBestSellerBook = onRequestRefreshBookList,
            onRequestRecommendBook = onRequestRecommendBookList,
            onRequestNewBook = onRequestNewBookList,
            onClickBook = { Toast.makeText(context, "click : ${it.title}", Toast.LENGTH_SHORT).show() }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BookTabRow(
    coroutineScope: CoroutineScope,
    pageState: PagerState,
    titles: List<String>
) {
    TabRow(
        selectedTabIndex = pageState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pageState, tabPositions)
            )
        }
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = pageState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pageState.scrollToPage(index)
                    }
                }
            ) {
                Text(title)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BookHorizontalPager(
    modifier: Modifier,
    pageState: PagerState,
    state: State<MainState>,
    cellCount: Int,
    onRequestBestSellerBook: () -> Unit,
    onRequestRecommendBook: () -> Unit,
    onRequestNewBook: () -> Unit,
    onClickBook: (Book) -> Unit
) {
    HorizontalPager(
        modifier = modifier,
        count = 3,
        state = pageState
    ) { page ->
        when (page) {
            BookPage.BestSeller.ordinal -> {
                BookSwipeGrid(
                    books = state.value.bestSellerBooks,
                    isLoading = state.value.isLoadingBestSeller,
                    cellCount = cellCount,
                    onClickBook = onClickBook,
                    onSwipeRefresh = { onRequestBestSellerBook.invoke() }
                )
            }
            BookPage.Recommend.ordinal -> {
                BookSwipeGrid(
                    books = state.value.recommendBooks,
                    isLoading = state.value.isLoadingRecommend,
                    cellCount = cellCount,
                    onClickBook = onClickBook,
                    onSwipeRefresh = { onRequestRecommendBook.invoke() }
                )
            }

            BookPage.New.ordinal -> {
                BookSwipeGrid(
                    books = state.value.newBooks,
                    isLoading = state.value.isLoadingNew,
                    cellCount = cellCount,
                    onClickBook = onClickBook,
                    onSwipeRefresh = { onRequestNewBook.invoke() }
                )
            }
        }
    }
}

@Composable
fun BookSwipeGrid(
    modifier: Modifier = Modifier.fillMaxSize(),
    books: List<Book>,
    isLoading: Boolean,
    cellCount: Int,
    onSwipeRefresh: () -> Unit,
    onClickBook: (Book) -> Unit
) {
    SwipeRefresh(
        modifier = modifier,
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = onSwipeRefresh
    ) {
        BookGrid(
            books = books,
            cellCount = cellCount,
            onClickBook = onClickBook
        )
    }
}

@Composable
fun BookGrid(books: List<Book>, cellCount: Int, onClickBook: (Book) -> Unit) {
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
        modifier = Modifier
            .height(190.dp)
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