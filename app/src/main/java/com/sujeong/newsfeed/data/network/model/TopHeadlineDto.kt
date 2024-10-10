package com.sujeong.newsfeed.data.network.model

data class TopHeadlineDto(
    val source: SourceDto,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
)
