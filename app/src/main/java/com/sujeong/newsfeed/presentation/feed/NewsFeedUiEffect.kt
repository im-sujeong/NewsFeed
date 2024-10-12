package com.sujeong.newsfeed.presentation.feed

sealed class NewsFeedUiEffect {
    data class ShowErrorToast(val message: String?): NewsFeedUiEffect()
}