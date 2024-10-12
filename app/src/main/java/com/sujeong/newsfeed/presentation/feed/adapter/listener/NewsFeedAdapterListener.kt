package com.sujeong.newsfeed.presentation.feed.adapter.listener

import com.sujeong.newsfeed.domain.model.TopHeadline

interface NewsFeedAdapterListener {
    fun onClick(topHeadline: TopHeadline)
}