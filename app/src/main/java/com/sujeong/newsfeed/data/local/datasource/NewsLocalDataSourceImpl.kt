package com.sujeong.newsfeed.data.local.datasource

import androidx.paging.PagingSource
import com.sujeong.newsfeed.data.local.dao.NewsDao
import com.sujeong.newsfeed.data.local.entity.TopHeadlineEntity
import com.sujeong.newsfeed.data.source.NewsLocalDataSource
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(
    private val newsDao: NewsDao
): NewsLocalDataSource {
    override suspend fun insertTopHeadlines(topHeadlines: List<TopHeadlineEntity>) {
        newsDao.insertTopHeadlines(topHeadlines)
    }

    override fun getTopHeadlines(): PagingSource<Int, TopHeadlineEntity> {
        return newsDao.getTopHeadlines()
    }

    override suspend fun getReadTopHeadlines(): List<TopHeadlineEntity> {
        return newsDao.getReadTopHeadlines()
    }

    override suspend fun updateTopHeadlineRead(url: String) {
        newsDao.updateTopHeadlineRead(url)
    }

    override suspend fun clearTopHeadlines() {
        newsDao.clearTopHeadlines()
    }
}