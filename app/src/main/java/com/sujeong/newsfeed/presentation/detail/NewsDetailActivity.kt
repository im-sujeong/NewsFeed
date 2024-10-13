package com.sujeong.newsfeed.presentation.detail

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.view.WindowInsets.Type
import android.view.WindowInsetsController
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import com.sujeong.newsfeed.databinding.ActivityNewsDetailBinding
import com.sujeong.newsfeed.presentation.BaseActivity
import kotlinx.coroutines.launch

class NewsDetailActivity: BaseActivity<ActivityNewsDetailBinding>() {
    override fun getViewBinding() = ActivityNewsDetailBinding.inflate(layoutInflater)

    private val viewModel: NewsDetailViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun initViews() = with(binding){
        enableEdgeToEdge()

        root.setOnApplyWindowInsetsListener { view, insets ->
            val statusBarHeight = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insets.getInsets(
                    Type.systemBars()
                ).top
            }else {
                view.rootWindowInsets.systemWindowInsetTop
            }

            if(statusBarHeight > 0) {
                binding.toolbar.updatePadding(top = statusBarHeight)
            }

            insets
        }

        setToolbar(toolbar)
        hideSystemBars()

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                pbLoading.progress = newProgress
                pbLoading.isGone = newProgress >= 100
            }
        }

        with(webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
    }

    private fun hideSystemBars() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.apply {
                setDecorFitsSystemWindows(false)

                insetsController?.apply {
                    hide(Type.statusBars() or Type.navigationBars())

                    systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        }else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }

    override fun observeState(){
        lifecycleScope.launch {
            viewModel.state.collect {
                it.topHeadline?.let { topHeadline ->
                    binding.webView.loadUrl(topHeadline.url)
                    binding.tvTitle.text = topHeadline.title
                }
            }
        }
    }

    override fun finish() {
        super.finish()

        binding.webView.destroy()
    }
}