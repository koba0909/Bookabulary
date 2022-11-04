package com.koba.data.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class YoutubeSearchDto(
    val items: List<YoutubeSearchItemDto>
)

@Keep
@Serializable
data class YoutubeSearchItemDto(
    @SerialName("snippet")
    val item: YoutubeSearchSnippetDto,
    val id: YoutubeSearchVideoIdDto
)

@Keep
@Serializable
data class YoutubeSearchVideoIdDto(
    val videoId: String
)

@Keep
@Serializable
data class YoutubeSearchSnippetDto(
    val title: String,
    val thumbnails: YoutubeThumbnailDto,
    @SerialName("channelTitle")
    val channelName: String
)

@Keep
@Serializable
data class YoutubeThumbnailDto(
    @SerialName("default")
    val defaultSize: YoutubeThumbnailDefaultDto
)

@Keep
@Serializable
data class YoutubeThumbnailDefaultDto(
    val url: String,
    val width: Int,
    val height: Int
)
