package com.sujeong.newsfeed.di

import android.content.Context
import androidx.room.Room
import com.sujeong.newsfeed.data.local.NewsFeedDatabase
import com.sujeong.newsfeed.data.local.dao.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideNewsFeedDatabase(
        @ApplicationContext context: Context
    ): NewsFeedDatabase {
        return Room.databaseBuilder(
            context,
            NewsFeedDatabase::class.java,
            NewsFeedDatabase.DB_NAME
        ).build()
    }

    @Provides
    fun provideNewsDao(
        newsFeedDatabase: NewsFeedDatabase
    ): NewsDao {
        return newsFeedDatabase.newsDao
    }
}