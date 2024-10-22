package com.example.movieapp.presentation.fragments.moviepage_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.DetailsItem
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.PersonItem
import com.example.movieapp.domain.model.UserReviewsItem
import com.example.movieapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception


@HiltViewModel
class MoviePageViewModel @Inject constructor(val movieDetails: MoviesRepository): ViewModel() {
    val reviewsState = MutableStateFlow<List<UserReviewsItem>>(listOf())
    val detailsState = MutableStateFlow<DetailsItem?>(null)


    val movieState = MutableStateFlow<MovieItem?>(null)
    val castState = MutableStateFlow<List<PersonItem>>(listOf())
    val crewState = MutableStateFlow<List<PersonItem>>(listOf())


    fun getMovieDetails(id : Int){
        viewModelScope.launch {
            detailsState.update {

                movieDetails.getDetails(id)

            }
        }

        viewModelScope.launch {
            reviewsState.update {
                try {
                    movieDetails.getMovieDetails(id)

                }
                catch (e : Exception){
                    listOf()
                }
            }
        }
        viewModelScope.launch {
            movieState.update {
                movieDetails.getExactMovie(id)
            }
        }
        viewModelScope.launch {
            castState.update {
                try {
                    movieDetails.getCasts(id)
                }
                catch (e : Exception){
                    listOf()}
            }
        }
        viewModelScope.launch {
            crewState.update {
                try {
                    movieDetails.getCrew(id)

                }
                catch (e : Exception){
                    listOf()
                }
            }
        }

    }


}