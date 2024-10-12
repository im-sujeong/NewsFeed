package com.sujeong.newsfeed.data.source

import androidx.paging.PagingSource
import com.sujeong.newsfeed.data.local.entity.TopHeadlineEntity

interface NewsLocalDataSource {
    suspend fun insertTopHeadlines(topHeadlines: List<TopHeadlineEntity>)

    fun getTopHeadlines(): PagingSource<Int, TopHeadlineEntity>

    suspend fun getReadTopHeadlines(): List<TopHeadlineEntity>

    suspend fun updateTopHeadlineRead(url: String)

    suspend fun clearTopHeadlines()
}