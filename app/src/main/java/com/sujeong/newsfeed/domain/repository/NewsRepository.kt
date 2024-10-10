package com.sujeong.newsfeed.domain.repository

interface NewsRepository {
    suspend fun fetchTopHeadlines()
}