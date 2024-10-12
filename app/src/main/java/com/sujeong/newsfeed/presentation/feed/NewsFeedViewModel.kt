package com.sujeong.newsfeed.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sujeong.newsfeed.domain.NewsFeedException
import com.sujeong.newsfeed.domain.usecase.FetchTopHeadlines
import com.sujeong.newsfeed.domain.usecase.UpdateTopHeadlineRead
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val fetchTopHeadlines: FetchTopHeadlines,
    private val updateTopHeadlineRead: UpdateTopHeadlineRead
): ViewModel() {
    private val _newsFeedState = MutableStateFlow(NewsFeedState())
    val newsFeedState: StateFlow<NewsFeedState> = _newsFeedState.onStart {
        fetchNewsFeed()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NewsFeedState()
    )

    fun onAction(newsFeedIntent: NewsFeedIntent) {
        when(newsFeedIntent) {
            is NewsFeedIntent.ClickNews -> viewModelScope.launch {
                updateTopHeadlineRead(newsFeedIntent.topHeadline)
            }
        }
    }

    private fun fetchNewsFeed() = viewModelScope.launch(
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
        fetchTopHeadlines()
            .cachedIn(viewModelScope)
            .collect {
                _newsFeedState.value = _newsFeedState.value.copy(
                    isLoading = false,
                    topHeadlines = it
                )
            }
    }
}