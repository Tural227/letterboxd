package com.example.movieapp.presentation.fragments.home_fragment

import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.SecondaryMovieItem
import com.example.movieapp.domain.model.UserReviewsItem
import com.example.movieapp.domain.repository.MoviesRepository
import com.example.movieapp.domain.repository.ReviewsRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val popularMovies: MoviesRepository,
                                        val sharedPreferences: SharedPreferences,
                                        val firebaseAuth: FirebaseAuth,
                                        val userReviews: ReviewsRepository,
                                        val movieDao: MovieDao,
                                        val dataStore: DataStore<Preferences>): ViewModel() {
    val moviesState = MutableStateFlow<List<SecondaryMovieItem>>(emptyList())
    val topRatedState = MutableStateFlow<List<List<SecondaryMovieItem>>>(emptyList())
    val reviewsState = MutableStateFlow<List<UserReviewsItem>>(emptyList())




    val isLoadingOne = MutableStateFlow(false)
    val isLoadingTwo = MutableStateFlow(false)
    val isLoadingThree = MutableStateFlow(false)


    init {
        viewModelScope.launch {
            isLoadingOne.update { true }
            moviesState.update { popularMovies.getRealPopularMovies(1) }
            isLoadingOne.update { false }
        }
        viewModelScope.launch {

            isLoadingTwo.update { true }
            topRatedState.update { popularMovies.getStackedPopularMovies() }
            isLoadingTwo.update { false }

        }
        viewModelScope.launch {

            isLoadingThree.update { true }
            reviewsState.update { userReviews.getReviews() }.also {
                Log.e("DATA","Salam")
            }
            isLoadingThree.update { false }
        }
    }
}