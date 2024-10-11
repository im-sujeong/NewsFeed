package com.sujeong.newsfeed.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sujeong.newsfeed.data.NewsRemoteMediator
import com.sujeong.newsfeed.data.local.NewsFeedDatabase
import com.sujeong.newsfeed.data.mapper.toTopHeadline
import com.sujeong.newsfeed.data.source.NewsLocalDataSource
import com.sujeong.newsfeed.data.source.NewsNetworkDataSource
import com.sujeong.newsfeed.domain.model.TopHeadline
import com.sujeong.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsFeedDatabase: NewsFeedDatabase,
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsNetworkDataSource: NewsNetworkDataSource,
): NewsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun fetchTopHeadlines(): Flow<PagingData<TopHeadline>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = NewsRemoteMediator(
                newsFeedDatabase = newsFeedDatabase,
                newsNetworkDataSource = newsNetworkDataSource,
                newsLocalDataSource = newsLocalDataSource
            ),
            pagingSourceFactory = {
                newsLocalDataSource.getTopHeadlines()
            }
        ).flow.map {
            it.map { topHeadlineEntity ->
                topHeadlineEntity.toTopHeadline()
            }
        }
    }
}