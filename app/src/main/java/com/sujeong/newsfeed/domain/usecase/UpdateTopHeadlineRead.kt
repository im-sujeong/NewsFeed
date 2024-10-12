package com.sujeong.newsfeed.domain.usecase

import com.sujeong.newsfeed.domain.model.TopHeadline
import com.sujeong.newsfeed.domain.repository.NewsRepository
import javax.inject.Inject

class UpdateTopHeadlineRead @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(topHeadline: TopHeadline) {
        if(!topHeadline.isRead) {
            newsRepository.updateTopHeadlineRead(topHeadline.url)
        }
    }
}