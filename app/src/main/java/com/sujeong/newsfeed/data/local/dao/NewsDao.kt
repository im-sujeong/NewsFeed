package com.sujeong.newsfeed.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sujeong.newsfeed.data.local.entity.TopHeadlineEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlines(topHeadlines: List<TopHeadlineEntity>)

    @Query("SELECT * FROM topheadlineentity")
    fun getTopHeadlines(): PagingSource<Int, TopHeadlineEntity>

    @Query("SELECT * FROM topheadlineentity WHERE isRead = 1")
    suspend fun getReadTopHeadlines(): List<TopHeadlineEntity>

    @Query("UPDATE topheadlineentity SET isRead = 1 WHERE url = :url")
    suspend fun updateTopHeadlineRead(url: String)

    @Query("DELETE FROM topheadlineentity")
    suspend fun clearTopHeadlines()
}