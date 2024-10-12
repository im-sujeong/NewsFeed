package com.sujeong.newsfeed.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sujeong.newsfeed.domain.NewsFeedException
import com.sujeong.newsfeed.domain.usecase.FetchTopHeadlines
import com.sujeong.newsfeed.domain.usecase.UpdateTopHeadlineRead
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _newsFeedUiEffect = MutableSharedFlow<NewsFeedUiEffect>()
    val newsFeedUiEffect: SharedFlow<NewsFeedUiEffect> = _newsFeedUiEffect

    fun onAction(newsFeedIntent: NewsFeedIntent) {
        when(newsFeedIntent) {
            is NewsFeedIntent.ClickNews -> viewModelScope.launch {
                updateTopHeadlineRead(newsFeedIntent.topHeadline)
            }

            is NewsFeedIntent.OnPagingError -> handleError(newsFeedIntent.error)
        }
    }

    private fun fetchNewsFeed() = viewModelScope.launch(
        CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
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

    private fun handleError(throwable: Throwable) {
        Timber.e("$throwable")

        if(throwable is NewsFeedException) {
            // NewsFeedException 처리
            when(throwable) {
                is NewsFeedException.ApiKeyInvalid,
                is NewsFeedException.ApiKeyMissing,
                is NewsFeedException.ParameterMissing,
                is NewsFeedException.RateLimited -> {
                    viewModelScope.launch {
                        _newsFeedUiEffect.emit(NewsFeedUiEffect.ShowErrorToast(throwable.message))
                    }
                }
                NewsFeedException.UnknownServerError -> Unit
                NewsFeedException.DisconnectNetwork -> Unit
            }
        }else {
            // 그 외 Exception 처리
        }

        _newsFeedState.value = _newsFeedState.value.copy(
            isLoading = false
        )
    }
}