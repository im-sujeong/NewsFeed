package com.sujeong.newsfeed.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseErrorResponseDto(
    val status: String,
    val code: String,
    val message: String
)