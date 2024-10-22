package com.example.movieapp.presentation.fragments.reviewspage_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReviewViewModel @Inject constructor(val moviesRepository: MoviesRepository,val movieDao: MovieDao): ViewModel() {
    val exactMovie = MutableStateFlow<MovieItem?>(null)


    fun getMovie(movieID: Int){
        viewModelScope.launch {
            exactMovie.update {
                moviesRepository.getExactMovie(movieID)
            }
        }
    }



}