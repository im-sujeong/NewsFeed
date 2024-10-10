package com.sujeong.newsfeed.data.network.mapper

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.sujeong.newsfeed.data.network.model.BaseErrorResponseDto
import com.sujeong.newsfeed.data.network.model.BaseResponseDto
import com.sujeong.newsfeed.domain.NewsFeedException
import retrofit2.Response

@OptIn(ExperimentalStdlibApi::class)
fun <T> Response<BaseResponseDto<T>>.toData(): T {
    return if (isSuccessful) {
        body()?.articles ?: throw NewsFeedException.UnknownServerError
    } else {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<BaseErrorResponseDto>()

        throw errorBody()?.string()?.let { errorBody ->
            jsonAdapter.fromJson(errorBody)?.let {
                when (it.status) {
                    "apiKeyMissing" -> NewsFeedException.ApiKeyMissing(it.message)
                    "apiKeyInvalid" -> NewsFeedException.ApiKeyInvalid(it.message)
                    "parametersMissing" -> NewsFeedException.ParameterMissing(it.message)
                    else -> NewsFeedException.UnknownServerError
                }
            }
        } ?: NewsFeedException.UnknownServerError
    }
}