package com.sujeong.newsfeed.data.source

import com.sujeong.newsfeed.data.network.model.TopHeadlineDto

interface NewsNetworkDataSource {
    suspend fun fetchTopHeadlines(
        page: Int,
        pageSize: Int
    ): List<TopHeadlineDto>
}