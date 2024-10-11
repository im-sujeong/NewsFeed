package com.sujeong.newsfeed.data.network.datasource

import com.sujeong.newsfeed.data.source.NewsNetworkDataSource
import com.sujeong.newsfeed.data.network.api.NewsApi
import com.sujeong.newsfeed.data.network.mapper.toData
import com.sujeong.newsfeed.data.network.model.TopHeadlineDto
import javax.inject.Inject

class NewsNetworkDataSourceImpl @Inject constructor(
    private val newsApi: NewsApi
): NewsNetworkDataSource{
    override suspend fun fetchTopHeadlines(
        page: Int,
        pageSize: Int
    ): List<TopHeadlineDto> {
        return newsApi.fetchTopHeadlines(page, pageSize).toData()
    }
}