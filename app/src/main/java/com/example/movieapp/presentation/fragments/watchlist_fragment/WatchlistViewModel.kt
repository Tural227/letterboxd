package com.example.movieapp.presentation.fragments.watchlist_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.domain.model.WatchlistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(val movieDao: MovieDao):ViewModel() {
    val watchListState = MutableStateFlow<List<WatchlistItem>>(listOf())


    fun getWatchlist(){
        viewModelScope.launch {
            watchListState.update {
                movieDao.getWatchlistItem()
            }
        }
    }



}