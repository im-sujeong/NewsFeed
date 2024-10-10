package com.sujeong.newsfeed.data.datasource

import com.sujeong.newsfeed.data.network.model.TopHeadlineDto

interface NewsNetworkDataSource {
    suspend fun fetchTopHeadlines(): List<TopHeadlineDto>
}