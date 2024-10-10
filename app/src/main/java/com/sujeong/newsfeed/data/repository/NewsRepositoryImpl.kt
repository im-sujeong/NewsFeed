package com.sujeong.newsfeed.data.repository

import com.sujeong.newsfeed.data.datasource.NewsNetworkDataSource
import com.sujeong.newsfeed.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsNetworkDataSource: NewsNetworkDataSource
): NewsRepository {
    override suspend fun fetchTopHeadlines() {
        newsNetworkDataSource.fetchTopHeadlines()
        //TODO 로컬 데이터 Update
    }
}