package com.sujeong.newsfeed.presentation.feed

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.sujeong.newsfeed.databinding.ActivityNewsFeedBinding
import com.sujeong.newsfeed.domain.model.TopHeadline
import com.sujeong.newsfeed.presentation.BaseActivity
import com.sujeong.newsfeed.presentation.detail.NewsDetailActivity
import com.sujeong.newsfeed.presentation.detail.NewsDetailViewModel
import com.sujeong.newsfeed.presentation.feed.adapter.NewsFeedAdapter
import com.sujeong.newsfeed.presentation.feed.adapter.listener.NewsFeedAdapterListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedActivity: BaseActivity<ActivityNewsFeedBinding>() {
    override fun getViewBinding() = ActivityNewsFeedBinding.inflate(layoutInflater)

    private val viewModel: NewsFeedViewModel by viewModels()

    private val newsFeedAdapter by lazy {
        NewsFeedAdapter(object : NewsFeedAdapterListener {
            override fun onClick(topHeadline: TopHeadline) {
                openNewsDetail(topHeadline)
            }
        })
    }

    override fun initViews() = with(binding) {
        rvNewsFeed.adapter = newsFeedAdapter

        newsFeedAdapter.addLoadStateListener { loadState ->
            val error = if(loadState.refresh is LoadState.Error) {
                (loadState.refresh as LoadState.Error).error
            }else if(loadState.append is LoadState.Error) {
                (loadState.append as LoadState.Error).error
            }else if(loadState.prepend is LoadState.Error) {
                (loadState.prepend as LoadState.Error).error
            }else {
                return@addLoadStateListener
            }

            viewModel.onAction(newsFeedIntent = NewsFeedIntent.OnPagingError(error))
        }
    }

    override fun observeState() {
        lifecycleScope.launch {
            viewModel.newsFeedState.collect { state ->
                binding.pbLoading.isVisible = state.isLoading

                newsFeedAdapter.submitData(
                    state.topHeadlines
                )
            }
        }

        lifecycleScope.launch {
            viewModel.newsFeedUiEffect.collectLatest { effect ->
                when(effect) {
                    is NewsFeedUiEffect.ShowErrorToast -> {
                        Toast.makeText(
                            this@NewsFeedActivity,
                            effect.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun openNewsDetail(topHeadline: TopHeadline) {
        viewModel.onAction(newsFeedIntent = NewsFeedIntent.ClickNews(topHeadline))

        startActivity(
            Intent(
                this,
                NewsDetailActivity::class.java
            ).apply {
                putExtra(NewsDetailViewModel.KEY_TOP_HEADLINE, topHeadline)
            }
        )
    }
}