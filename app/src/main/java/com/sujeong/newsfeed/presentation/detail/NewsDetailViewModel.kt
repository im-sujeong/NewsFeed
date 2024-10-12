package com.sujeong.newsfeed.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sujeong.newsfeed.domain.model.TopHeadline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    val state: StateFlow<NewsDetailState> = _state
}