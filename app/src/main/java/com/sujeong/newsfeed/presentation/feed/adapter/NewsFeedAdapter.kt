package com.sujeong.newsfeed.presentation.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sujeong.newsfeed.databinding.ItemNewsFeedBinding
import com.sujeong.newsfeed.domain.model.TopHeadline
import com.sujeong.newsfeed.presentation.feed.adapter.listener.NewsFeedAdapterListener
import com.sujeong.newsfeed.presentation.feed.viewholder.NewsFeedViewHolder

class NewsFeedAdapter(
    private val newsFeedAdapterListener: NewsFeedAdapterListener
): PagingDataAdapter<TopHeadline, NewsFeedViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopHeadline>() {
            override fun areItemsTheSame(oldItem: TopHeadline, newItem: TopHeadline): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: TopHeadline, newItem: TopHeadline): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        return NewsFeedViewHolder(
            ItemNewsFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        holder.bind(getItem(position), newsFeedAdapterListener)
    }
}