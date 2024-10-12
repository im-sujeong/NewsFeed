package com.sujeong.newsfeed.presentation.feed

import com.sujeong.newsfeed.domain.model.TopHeadline

sealed class NewsFeedIntent {
    data class ClickNews(
        val topHeadline: TopHeadline
    ): NewsFeedIntent()

    data class OnPagingError(
        val error: Throwable
    ): NewsFeedIntent()
}