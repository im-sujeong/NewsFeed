package com.sujeong.newsfeed.presentation.feed.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sujeong.newsfeed.databinding.ItemNewsFeedBinding
import com.sujeong.newsfeed.domain.model.TopHeadline
import com.sujeong.newsfeed.presentation.feed.adapter.listener.NewsFeedAdapterListener
import com.sujeong.newsfeed.util.extensions.clear
import com.sujeong.newsfeed.util.extensions.loadImage
import java.time.format.DateTimeFormatter

class NewsFeedViewHolder(
    private val binding: ItemNewsFeedBinding
): ViewHolder(binding.root) {
    fun bind(
        topHeadline: TopHeadline?,
        newsFeedAdapterListener: NewsFeedAdapterListener
    ) = with(binding){
        ivImage.clear()

        topHeadline?.let {
            with(it) {
                tvTitle.isSelected = isRead

                tvTitle.text = title
                tvSource.text = source
                tvDate.text = publishedAt.format(
                    DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
                )

                ivImage.loadImage(
                    url = urlToImage,
                    cornerDP = 4
                )

                root.setOnClickListener {
                    newsFeedAdapterListener.onClick(topHeadline)
                }
            }
        }
    }
}