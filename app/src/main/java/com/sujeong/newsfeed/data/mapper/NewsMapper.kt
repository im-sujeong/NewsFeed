package com.sujeong.newsfeed.data.mapper

import com.sujeong.newsfeed.data.local.entity.TopHeadlineEntity
import com.sujeong.newsfeed.data.network.model.TopHeadlineDto
import com.sujeong.newsfeed.domain.model.TopHeadline
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun List<TopHeadlineDto>.toTopHeadlineEntities(
    readTopHeadlineEntities: List<TopHeadlineEntity> = listOf()
): List<TopHeadlineEntity> {
    return map { topHeadlineDto ->
        topHeadlineDto.toTopHeadlineEntity(
            readTopHeadlineEntities.find {
                it.url == topHeadlineDto.url
            } != null
        )
    }
}

fun TopHeadlineDto.toTopHeadlineEntity(
    isRead: Boolean
): TopHeadlineEntity {
    return TopHeadlineEntity(
        source = source.name,
        title = title,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        isRead = isRead,
        isRemoved = title == "[Removed]"
    )
}

fun TopHeadlineEntity.toTopHeadline(): TopHeadline {
    return TopHeadline(
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage,
        publishedAt = LocalDateTime.parse(publishedAt, DateTimeFormatter.ISO_DATE_TIME),
        isRead = isRead,
        isRemoved = isRemoved
    )
}