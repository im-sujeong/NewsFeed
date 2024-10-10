package com.sujeong.newsfeed.domain

import java.lang.Exception

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
}