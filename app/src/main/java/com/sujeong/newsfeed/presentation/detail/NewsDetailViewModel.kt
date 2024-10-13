package com.sujeong.newsfeed.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujeong.newsfeed.domain.model.TopHeadline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    companion object {
        const val KEY_TOP_HEADLINE = "topHeadline"
    }

    private val topHeadline = savedStateHandle.get<TopHeadline>(KEY_TOP_HEADLINE)

    private val _state = MutableStateFlow(NewsDetailState(topHeadline))
    val state: StateFlow<NewsDetailState> = _state.onStart {
        hideToolbarSection()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NewsDetailState(topHeadline)
    )

    private var toggleToolbarJob: Job? = null

    fun onAction(newsDetailIntent: NewsDetailIntent) {
        when(newsDetailIntent) {
            NewsDetailIntent.ToggleToolbarSection -> {
                toggleToolbarJob?.cancel()

                _state.value = _state.value.copy(
                    isToolbarVisible = !_state.value.isToolbarVisible
                )

                if(_state.value.isToolbarVisible) {
                    hideToolbarSection()
                }
            }
        }
    }

    private fun hideToolbarSection() {
        toggleToolbarJob = viewModelScope.launch{
            delay(3000)

            _state.value = _state.value.copy(
                isToolbarVisible = false
            )
        }
    }
}