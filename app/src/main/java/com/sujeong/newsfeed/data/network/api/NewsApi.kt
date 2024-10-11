package com.sujeong.newsfeed.data.network.api

import com.sujeong.newsfeed.data.network.model.BaseResponseDto
import com.sujeong.newsfeed.data.network.model.TopHeadlineDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines?country=us")
    suspend fun fetchTopHeadlines(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): Response<BaseResponseDto<List<TopHeadlineDto>>>
}