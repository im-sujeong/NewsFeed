package com.sujeong.newsfeed.data.local.entity

import androidx.room.Entity

@Entity(
    primaryKeys = ["url", "publishedAt"]
)
data class TopHeadlineEntity(
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val isRead: Boolean,
    val isRemoved: Boolean
)
