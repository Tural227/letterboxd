package com.example.movieapp.presentation.fragments.likes_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.domain.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikesViewModel  @Inject constructor(val movieDao: MovieDao) : ViewModel() {
    val likesState = MutableStateFlow<List<MovieItem>>(listOf())

    fun getLikes(){
        viewModelScope.launch {
            likesState.update {
                movieDao.getFavs()
            }
        }
    }


}