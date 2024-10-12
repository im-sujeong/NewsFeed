package com.sujeong.newsfeed.domain

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.Exception

sealed class NewsFeedException(
    override val message: String? = null
): Exception() {
    data object UnknownServerError: NewsFeedException() {
        private fun readResolve(): Any = UnknownServerError
    }

    data class ApiKeyMissing(
        override val message: String?
    ): NewsFeedException(message)

    data class ApiKeyInvalid(
        override val message: String?
    ): NewsFeedException(message)

    data class ParameterMissing(
        override val message: String?
    ): NewsFeedException(message)

    data object DisconnectNetwork: NewsFeedException() {
        private fun readResolve(): Any = DisconnectNetwork
    }

    companion object {
        fun toNewsFeedException(e: Exception): Exception {
            return when(e) {
                is UnknownHostException,
                is SocketTimeoutException,
                is java.net.ConnectException -> DisconnectNetwork
                else -> e
            }
        }
    }
}