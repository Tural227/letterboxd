package com.example.movieapp.presentation.fragments.user_profile.background_fragment

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.BackgroundItem
import com.example.movieapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BackgroundFragmentViewModel @Inject constructor(val moviesRepository: MoviesRepository, val dataStore: DataStore<Preferences>) : ViewModel() {
    val moviesState = MutableStateFlow<List<BackgroundItem>>(listOf())
    init {
        viewModelScope.launch {
            moviesState.update { moviesRepository.getBackgroundImages() }
        }
    }
}