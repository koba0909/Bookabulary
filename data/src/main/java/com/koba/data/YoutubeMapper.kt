package com.koba.data

import com.koba.data.dto.YoutubeSearchDto
import com.koba.domain.model.YoutubeSearchResult

fun YoutubeSearchDto.toYoutubeSearchResults(): List<YoutubeSearchResult> {
    return items.map {
        YoutubeSearchResult(
            videoLink = "https://www.youtube.com/watch?v=${it.id.videoId}",
            title = it.item.title,
            thumbnailUrl = it.item.thumbnails.defaultSize.url,
            channelName = it.channelName
        )
    }
}
