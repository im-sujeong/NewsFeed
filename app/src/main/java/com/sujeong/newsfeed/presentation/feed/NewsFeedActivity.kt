package com.sujeong.newsfeed.presentation.feed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sujeong.newsfeed.databinding.ActivityNewsFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFeedActivity: AppCompatActivity() {
    private val binding by lazy {
        ActivityNewsFeedBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}