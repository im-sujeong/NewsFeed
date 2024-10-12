package com.sujeong.newsfeed.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujeong.newsfeed.domain.NewsFeedException
import com.sujeong.newsfeed.domain.usecase.FetchTopHeadlines
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val fetchTopHeadlines: FetchTopHeadlines
): ViewModel(){
    private val _state = MutableStateFlow(NewsFeedState())
    val state: StateFlow<NewsFeedState> = _state

    fun fetchNewsFeed() = viewModelScope.launch(
        CoroutineExceptionHandler { _, throwable ->
            Timber.e("$throwable")

            if(throwable is NewsFeedException) {
                // NewsFeedException 처리
                when(throwable) {
                    is NewsFeedException.ApiKeyInvalid -> Unit
                    is NewsFeedException.ApiKeyMissing -> Unit
                    is NewsFeedException.ParameterMissing -> Unit
                    NewsFeedException.UnknownServerError -> Unit
                    NewsFeedException.DisconnectNetwork -> Unit
                }
            }else {
                // 그 외 Exception 처리
            }
        }
    ){
        fetchTopHeadlines().collect {
            _state.value = _state.value.copy(
                isLoading = false,
                topHeadlines = it
            )
        }
    }
}