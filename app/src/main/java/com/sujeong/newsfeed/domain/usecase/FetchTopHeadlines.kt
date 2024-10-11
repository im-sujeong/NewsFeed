package com.sujeong.newsfeed.domain.usecase

import androidx.paging.PagingData
import com.sujeong.newsfeed.domain.model.TopHeadline
import com.sujeong.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTopHeadlines @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): Flow<PagingData<TopHeadline>> {
        return newsRepository.fetchTopHeadlines()
    }
}