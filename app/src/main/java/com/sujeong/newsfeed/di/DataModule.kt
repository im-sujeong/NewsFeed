package com.sujeong.newsfeed.di

import com.sujeong.newsfeed.data.local.datasource.NewsLocalDataSourceImpl
import com.sujeong.newsfeed.data.source.NewsNetworkDataSource
import com.sujeong.newsfeed.data.network.datasource.NewsNetworkDataSourceImpl
import com.sujeong.newsfeed.data.repository.NewsRepositoryImpl
import com.sujeong.newsfeed.data.source.NewsLocalDataSource
import com.sujeong.newsfeed.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    abstract fun bindNewsNetworkDataSource(
        newsNetworkDataSourceImpl: NewsNetworkDataSourceImpl
    ): NewsNetworkDataSource

    @Binds
    abstract fun bindNewsLocalDataSource(
        newsLocalDataSourceImpl: NewsLocalDataSourceImpl
    ): NewsLocalDataSource
}