package com.sujeong.newsfeed.data.network.api

import com.sujeong.newsfeed.data.network.model.BaseResponseDto
import com.sujeong.newsfeed.data.network.model.TopHeadlineDto
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {
    @GET("v2/top-headlines?country=us")
    suspend fun fetchTopHeadlines(): Response<BaseResponseDto<List<TopHeadlineDto>>>
}