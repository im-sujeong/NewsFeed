package com.sujeong.newsfeed.presentation.detail

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.sujeong.newsfeed.databinding.ActivityNewsDetailBinding
import com.sujeong.newsfeed.presentation.BaseActivity
import kotlinx.coroutines.launch

class NewsDetailActivity: BaseActivity<ActivityNewsDetailBinding>() {
    override fun getViewBinding() = ActivityNewsDetailBinding.inflate(layoutInflater)

    private val viewModel: NewsDetailViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun initViews() = with(binding){
        setToolbar(toolbar)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                binding.pbLoading.isGone = true
            }
        }

        with(webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
    }

    override fun observeState() = lifecycleScope.launch {
        viewModel.state.collect {
            it.topHeadline?.let { topHeadline ->
                binding.webView.loadUrl(topHeadline.url)

                title = topHeadline.title
            }
        }
    }

    override fun finish() {
        super.finish()

        binding.webView.destroy()
    }
}