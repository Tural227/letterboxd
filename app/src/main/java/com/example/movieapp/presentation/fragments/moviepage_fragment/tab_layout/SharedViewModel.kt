package com.example.movieapp.presentation.fragments.moviepage_fragment.tab_layout

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.DetailsItem
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.PersonItem
import com.example.movieapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(val movieDetails: MoviesRepository): ViewModel() {
    val castsState = MutableStateFlow<List<PersonItem>>(listOf())
    val crewState = MutableStateFlow<List<String>>(listOf())
    val detailsState = MutableStateFlow<DetailsItem?>(null)


    fun getCasts(id: Int){
        viewModelScope.launch {
            castsState.update { movieDetails.getCasts(id) }
        }
    }



    fun getDetails(movieID : Int){
        viewModelScope.launch {
            detailsState.update {
                movieDetails.getDetails(movieID)
            }
        }
    }

}