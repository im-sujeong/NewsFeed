package com.sujeong.newsfeed.presentation.feed.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sujeong.newsfeed.R
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
                tvSource.isVisible = !isRemoved
                ivBullet.isVisible = !isRemoved
                tvDate.isVisible = !isRemoved

                tvTitle.isEnabled = !isRemoved
                tvTitle.isSelected = isRead

                if(isRemoved) {
                    tvTitle.text = root.context.getText(R.string.removed_news)
                }else {
                    tvTitle.text = title
                    tvSource.text = source
                    tvDate.text = publishedAt.format(
                        DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
                    )
                }

                ivImage.loadImage(
                    url = urlToImage,
                    cornerDP = 4
                )

                root.isEnabled = !isRemoved
                root.setOnClickListener {
                    newsFeedAdapterListener.onClick(topHeadline)
                }
            }
        }
    }
}