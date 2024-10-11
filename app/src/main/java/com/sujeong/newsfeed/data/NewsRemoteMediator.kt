package com.sujeong.newsfeed.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sujeong.newsfeed.data.local.NewsFeedDatabase
import com.sujeong.newsfeed.data.local.entity.TopHeadlineEntity
import com.sujeong.newsfeed.data.mapper.toTopHeadlineEntities
import com.sujeong.newsfeed.data.source.NewsLocalDataSource
import com.sujeong.newsfeed.data.source.NewsNetworkDataSource

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val newsFeedDatabase: NewsFeedDatabase,
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsNetworkDataSource: NewsNetworkDataSource,
): RemoteMediator<Int, TopHeadlineEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopHeadlineEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    if(lastItem == null) {
                        1
                    }else {
                        val totalDataSize = state.pages.lastOrNull()?.data?.size ?: 0

                        totalDataSize / state.config.pageSize + 1
                    }
                }
            }

            val topHeadlines = newsNetworkDataSource.fetchTopHeadlines(
                page = loadKey,
                pageSize = state.config.pageSize
            )

            newsFeedDatabase.withTransaction {
                var readTopHeadlines: List<TopHeadlineEntity> = listOf()

                if(loadType == LoadType.REFRESH) {
                    //새로 고침 했을 경우, 읽은 기사를 가져온 후 초기화
                    readTopHeadlines = newsLocalDataSource.getReadTopHeadlines()

                    newsLocalDataSource.clearTopHeadlines()
                }

                newsLocalDataSource.insertTopHeadlines(
                    topHeadlines.toTopHeadlineEntities(
                        readTopHeadlines
                    )
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = topHeadlines.size < state.config.pageSize
            )
        }catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}