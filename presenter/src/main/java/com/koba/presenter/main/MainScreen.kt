package com.koba.presenter.main

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.koba.presenter.R
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
        Card(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(),
            elevation = 4.dp,
            shape = RectangleShape
        ) {
            BookTabRow(
                modifier = Modifier.height(30.dp)
                    .padding(start = 15.dp)
                    .background(Color.White),
                coroutineScope = coroutineScope,
                pageState = pageState,
                titles = tabs
            )
        }


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
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    pageState: PagerState,
    titles: List<String>
) {
    Row(modifier = modifier) {
        Image(
            modifier = Modifier.align(Alignment.CenterVertically)
                .size(16.dp),
            painter = painterResource(id = R.drawable.ic_hamburger),
            contentDescription = null
        )

        TabRow(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 10.dp)
                .background(Color.White),
            selectedTabIndex = pageState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pageState, tabPositions)
                        .fillMaxHeight()
                        .alpha(0.1f)
                        .clip(
                            RoundedCornerShape(100.dp)
                        )
                        .padding(vertical = 3.dp),
                    color = Color.Blue,
                    height = 15.dp
                )
            }
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.background(Color.White),
                    selected = pageState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pageState.scrollToPage(index)
                        }
                    }
                ) {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        color = if(pageState.currentPage == index) {
                            Color.Blue.copy(0.6f)
                        } else {
                            Color.Black.copy(0.9f)
                        },
                        fontWeight = if(pageState.currentPage == index) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Medium
                        }
                    )
                }
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
