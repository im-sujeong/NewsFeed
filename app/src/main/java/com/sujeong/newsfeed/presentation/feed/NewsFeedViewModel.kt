package com.sujeong.newsfeed.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujeong.newsfeed.domain.NewsFeedException
import com.sujeong.newsfeed.domain.usecase.FetchTopHeadlines
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val fetchTopHeadlines: FetchTopHeadlines
): ViewModel(){
    fun fetchNewsFeed() = viewModelScope.launch(
        CoroutineExceptionHandler { _, throwable ->
            if(throwable is NewsFeedException) {
                // NewsFeedException 처리
                when(throwable) {
                    is NewsFeedException.ApiKeyInvalid -> Unit
                    is NewsFeedException.ApiKeyMissing -> Unit
                    is NewsFeedException.ParameterMissing -> Unit
                    NewsFeedException.UnknownServerError -> Unit
                }
            }else {
                // 그 외 Exception 처리
            }
        }
    ){
        fetchTopHeadlines()
    }
}