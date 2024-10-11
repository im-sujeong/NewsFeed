package com.sujeong.newsfeed.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TopHeadlineEntity(
    val source: String,
    val title: String,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val isRead: Boolean
)
