package com.example.movieapp.presentation.fragments.search_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class MultiViewModel: ViewModel() {
    val genreState = MutableStateFlow<String?>(null)
    val checkClear = MutableStateFlow(false)


    fun selectGenre(genre: String){
        viewModelScope.launch{
            genreState.update { genre }
        }
    }

}