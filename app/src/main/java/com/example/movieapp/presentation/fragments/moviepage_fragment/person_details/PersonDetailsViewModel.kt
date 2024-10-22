package com.example.movieapp.presentation.fragments.moviepage_fragment.person_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.PersonDetailItem
import com.example.movieapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(val moviesRepository: MoviesRepository) :ViewModel() {
    val detailsState = MutableStateFlow<PersonDetailItem?>(null)

    fun getDetails(personID : Int){
        viewModelScope.launch {
            detailsState.update {
                moviesRepository.getPersonDetail(personID)
            }
        }
    }

}