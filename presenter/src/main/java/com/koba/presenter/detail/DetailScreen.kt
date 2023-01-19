package com.koba.presenter.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.koba.domain.model.Book
import com.koba.domain.model.YoutubeSearchResult
import com.koba.presenter.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@Preview
@Composable
fun DetailPreview() {
    DetailScreen(
        state = DetailState(
            book = Book(
                title = "테스트 중",
                imageUrl = "https://bimage.interpark.com/partner/goods_image/8/0/8/6/355168086s.jpg"
            ),
            youtubeSearchResults = listOf(
                YoutubeSearchResult(
                    title = "유튜브 테스트",
                    thumbnailUrl = "",
                    videoLink = "",
                    channelName = "유튜브 채널 이름"
                ),

                YoutubeSearchResult(
                    title = "유튜브 테스트2",
                    thumbnailUrl = "",
                    videoLink = "",
                    channelName = "유튜브 채널 이름2"
                )
            )
        ),
        effectFlow = MutableSharedFlow<DetailEffect>()
    )
}

@Composable
fun DetailScreen(
    state: DetailState,
    effectFlow: Flow<DetailEffect>
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DetailTitleRow(title = state.book.title)

        DetailContentColumn(
            modifier = Modifier.padding(horizontal = 10.dp),
            book = state.book,
            searchResults = state.youtubeSearchResults
        )
    }
}

@Composable
fun DetailContentColumn(
    modifier: Modifier = Modifier,
    book: Book,
    searchResults: List<YoutubeSearchResult>
) {
    Column(modifier = modifier) {
        BookInfoRow(modifier = Modifier, book = book)

        YoutubeColumn(
            modifier = Modifier.padding(10.dp),
            title = book.title,
            searchResults = searchResults
        )
    }
}

@Composable
fun BookInfoRow(modifier: Modifier = Modifier, book: Book) {
    Row(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .width(130.dp)
                .height(200.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.imageUrl)
                .memoryCacheKey(book.imageUrl)
                .placeholder(R.drawable.ic_hamburger)
                .build(),
            contentScale = ContentScale.FillBounds,
            contentDescription = book.description
        )

        BookDetailInfoColumn(
            modifier = Modifier.padding(start = 20.dp),
            book = book
        )
    }
}

@Composable
fun BookDetailInfoColumn(modifier: Modifier = Modifier, book: Book) {
    Column(modifier = modifier) {
        Text(text = "카테고리 : ${book.categoryName}")
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "작가 : ${book.author}")
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "가격 : ${String.format("%,d", book.priceStandard)}")
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "출판사 : ${book.publisher}")
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "출판일 : ${book.pubDate}")
    }
}

@Composable
fun YoutubeColumn(
    modifier: Modifier = Modifier,
    title: String,
    searchResults: List<YoutubeSearchResult>
) {
    Column(modifier = modifier) {
        YoutubeSearchResultTitleRow(
            modifier = Modifier.background(Color.Red.copy(alpha = 0.5f)),
            title = title
        )

        Spacer(modifier = Modifier.size(10.dp))

        YoutubeVideoList(searchResults)
    }
}

@Composable
fun YoutubeVideoList(searchResults: List<YoutubeSearchResult>) {
    LazyColumn(
        modifier = Modifier.background(Color.Green.copy(alpha = 0.5f))
            .fillMaxWidth()
            .height(400.dp)
    ) {
        items(searchResults) { item ->
            YoutubeVideoItemRow(item)
        }
    }
}

@Composable
fun YoutubeVideoItemRow(item: YoutubeSearchResult) {
    Row(
        modifier = Modifier.background(Color.Blue.copy(alpha = 0.5f))
            .fillMaxWidth()
            .height(70.dp)
    ) {
        AsyncImage(
            modifier = Modifier.width(64.dp)
                .height(36.dp)
                .background(Color.LightGray.copy(alpha = 0.5f)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.thumbnailUrl)
                .placeholder(R.drawable.ic_hamburger)
                .memoryCacheKey(item.thumbnailUrl)
                .build(),
            contentScale = ContentScale.FillBounds,
            contentDescription = item.title
        )

        Spacer(modifier = Modifier.size(10.dp))

        YoutubeItemDetailColumn(item = item)
    }
}

@Composable
fun YoutubeItemDetailColumn(modifier: Modifier = Modifier, item: YoutubeSearchResult) {
    Column(modifier = modifier) {
        Text(
            text = item.title,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = item.channelName,
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun YoutubeSearchResultTitleRow(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier,
        text = stringResource(
            id = R.string.youtube_title,
            title
        )
    )
}

@Composable
fun DetailTitleRow(modifier: Modifier = Modifier, title: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}
