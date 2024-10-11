package com.sujeong.newsfeed.domain.model

data class TopHeadline(
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val isRead: Boolean
)
