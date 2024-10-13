package com.sujeong.newsfeed.presentation.detail

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets.Type
import android.view.WindowInsetsController
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import com.sujeong.newsfeed.databinding.ActivityNewsDetailBinding
import com.sujeong.newsfeed.presentation.BaseActivity
import com.sujeong.newsfeed.util.extensions.getStatusBarHeight
import kotlinx.coroutines.launch

class NewsDetailActivity: BaseActivity<ActivityNewsDetailBinding>() {
    override fun getViewBinding() = ActivityNewsDetailBinding.inflate(layoutInflater)

    private val viewModel: NewsDetailViewModel by viewModels()

    private val gestureDetector by lazy {
        GestureDetector(this@NewsDetailActivity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                viewModel.onAction(NewsDetailIntent.ToggleToolbarSection)
                return super.onSingleTapConfirmed(e)
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun initViews() = with(binding){
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            root.setOnApplyWindowInsetsListener { view, windowInsets ->
                val topInsets = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    view.rootWindowInsets.getInsets(Type.systemBars()).top
                }else {
                    view.rootWindowInsets.systemWindowInsets.top
                }

                if(topInsets > 0) {
                    toolbar.updatePadding(top = topInsets)
                }

                windowInsets
            }
        }else {
            toolbar.updatePadding(top = getStatusBarHeight())
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

        webView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
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
                    if(binding.webView.url != null) return@let

                    binding.webView.loadUrl(topHeadline.url)
                    binding.tvTitle.text = topHeadline.title
                }

                toggleToolbarSection(it.isToolbarVisible)
            }
        }
    }

    override fun finish() {
        super.finish()

        binding.webView.destroy()
    }

    private fun toggleToolbarSection(isVisible: Boolean) = with(binding){
        val start = toolbar.alpha
        val end = if(isVisible) 1f else 0f

        ValueAnimator.ofFloat(start, end).apply {
            duration = 200

            addUpdateListener {
                toolbar.alpha = it.animatedValue as Float
                pbLoading.alpha = it.animatedValue as Float

                toolbar.isVisible = it.animatedValue as Float > 0f
                pbLoading.isVisible = it.animatedValue as Float > 0f
            }
        }.start()
    }
}