package com.example.movieapp.presentation.fragments.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.RatingItem
import com.example.movieapp.domain.model.UserReviewsItem
import com.example.movieapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserProfileViewModel @Inject constructor(val movieDao: MovieDao) : ViewModel() {
    val ratedMovies = MutableStateFlow<List<RatingItem>>(listOf())
    val favMovies = MutableStateFlow<List<MovieItem>>(listOf())
    val userReviews = MutableStateFlow<List<UserReviewsItem>>(listOf())



    fun userPreferences() {
        viewModelScope.launch {
            ratedMovies.update {
                movieDao.getRatedMovies()
            }
        }

        viewModelScope.launch {
            favMovies.update { movieDao.getFavs() }
        }

        viewModelScope.launch {
            userReviews.update {
                movieDao.getUserReviews()
            }
        }
    }
}