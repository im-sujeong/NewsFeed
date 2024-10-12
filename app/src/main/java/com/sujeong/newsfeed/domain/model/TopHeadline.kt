package com.sujeong.newsfeed.domain.model

import java.time.LocalDateTime

data class TopHeadline(
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: LocalDateTime,
    val isRead: Boolean,
    val isRemoved: Boolean
)