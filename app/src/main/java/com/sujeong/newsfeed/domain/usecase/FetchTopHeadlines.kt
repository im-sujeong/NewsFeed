package com.sujeong.newsfeed.domain.usecase

import com.sujeong.newsfeed.domain.repository.NewsRepository
import javax.inject.Inject

class FetchTopHeadlines @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke() {
        newsRepository.fetchTopHeadlines()
    }
}