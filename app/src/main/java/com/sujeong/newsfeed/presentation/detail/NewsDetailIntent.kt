package com.sujeong.newsfeed.presentation.detail

sealed class NewsDetailIntent {
    data object ToggleToolbarSection: NewsDetailIntent()
}