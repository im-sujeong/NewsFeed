package com.sujeong.newsfeed.data.mapper

import com.sujeong.newsfeed.data.local.entity.TopHeadlineEntity
import com.sujeong.newsfeed.data.network.model.TopHeadlineDto
import com.sujeong.newsfeed.domain.model.TopHeadline

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
        isRead = isRead
    )
}

fun TopHeadlineEntity.toTopHeadline(): TopHeadline {
    return TopHeadline(
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage,
        isRead = isRead
    )
}