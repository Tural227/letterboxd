package com.example.movieapp.presentation.fragments.search_fragment.filter_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FilterViewModel @Inject constructor(moviesRepository: MoviesRepository): ViewModel() {
    val genresState = MutableStateFlow<List<String>>(listOf())

    init {
        viewModelScope.launch {
            genresState.update {
                moviesRepository.getMovieGenres()
            }
        }

    }


}