package com.example.movieapp.presentation.fragments.search_fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.model.movie_genre.Genre
import com.example.movieapp.data.remote.myapi.MovieApi
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.repository.MoviesRepository
import com.google.api.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(val moviesRepository: MoviesRepository, val movieApi: MovieApi):ViewModel() {


    val feedState = MutableStateFlow<List<MovieItem>>(listOf())


    val isLoading = MutableStateFlow(false)

    val searchState = MutableStateFlow<List<MovieItem>>(listOf())

    val inputState = MutableStateFlow<String?>(null)

    var page = 1

    val genreState = MutableStateFlow<List<Genre>>(listOf())

    val realMyData = mutableListOf<MovieItem>()


    init {
        viewModelScope.launch {
            genreState.update {
                movieApi.getMovieGenres().genres
            }
        }



    }

    fun getNewPage(){
        viewModelScope.launch {
            try {
                val movies = moviesRepository.getPopularMovies(page)

                feedState.update { movies }
            }
            catch (e: Exception){
                Log.e("NO DATA",e.message.toString())
            }
            page++
            Log.e("PAGEs",page.toString())
        }
    }







    fun getSearchResult(movieName : String){
        viewModelScope.launch {
            searchState.update {
                moviesRepository.getSearchResults(movieName)
            }
        }
    }

}