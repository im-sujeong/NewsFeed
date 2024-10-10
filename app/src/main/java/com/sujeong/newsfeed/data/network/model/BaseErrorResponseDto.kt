package com.sujeong.newsfeed.data.network.model

data class BaseErrorResponseDto(
    val status: String,
    val code: String,
    val message: String
)
