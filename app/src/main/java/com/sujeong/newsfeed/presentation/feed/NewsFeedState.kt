package com.sujeong.newsfeed.presentation.feed

import androidx.paging.PagingData
import com.sujeong.newsfeed.domain.model.TopHeadline

data class NewsFeedState(
    val isLoading: Boolean = true,
    val topHeadlines: PagingData<TopHeadline> = PagingData.empty()
)