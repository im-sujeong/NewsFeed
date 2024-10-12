package com.sujeong.newsfeed.presentation.feed

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.sujeong.newsfeed.databinding.ActivityNewsFeedBinding
import com.sujeong.newsfeed.domain.model.TopHeadline
import com.sujeong.newsfeed.presentation.feed.adapter.NewsFeedAdapter
import com.sujeong.newsfeed.presentation.feed.adapter.listener.NewsFeedAdapterListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityNewsFeedBinding.inflate(layoutInflater)
    }

    private val viewModel: NewsFeedViewModel by viewModels()

    private val newsFeedAdapter by lazy {
        NewsFeedAdapter(object : NewsFeedAdapterListener {
            override fun onClick(topHeadline: TopHeadline) {

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        observeState()

        viewModel.fetchNewsFeed()
    }

    private fun initViews() = with(binding) {
        rvNewsFeed.adapter = newsFeedAdapter
    }

    private fun observeState() = lifecycleScope.launch {
        viewModel.state.collect { state ->
            binding.pbLoading.isVisible = state.isLoading

            newsFeedAdapter.submitData(
                state.topHeadlines
            )
        }
    }
}