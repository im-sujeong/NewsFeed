package com.sujeong.newsfeed.domain.repository

import androidx.paging.PagingData
import com.sujeong.newsfeed.domain.model.TopHeadline
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun fetchTopHeadlines(): Flow<PagingData<TopHeadline>>

    suspend fun updateTopHeadlineRead(url: String)
}