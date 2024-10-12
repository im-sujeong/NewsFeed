package com.sujeong.newsfeed.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class TopHeadline(
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: LocalDateTime,
    val isRead: Boolean,
    val isRemoved: Boolean
) : Parcelable