package com.sujeong.newsfeed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sujeong.newsfeed.data.local.dao.NewsDao
import com.sujeong.newsfeed.data.local.entity.TopHeadlineEntity

@Database(
    entities = [TopHeadlineEntity::class],
    version = 1
)
abstract class NewsFeedDatabase: RoomDatabase() {
    companion object {
        const val DB_NAME = "news_feed_db"
    }

    abstract val newsDao: NewsDao
}