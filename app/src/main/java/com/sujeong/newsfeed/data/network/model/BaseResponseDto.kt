package com.sujeong.newsfeed.data.network.model

data class BaseResponseDto<T>(
    val status: String,
    val articles: T
)
