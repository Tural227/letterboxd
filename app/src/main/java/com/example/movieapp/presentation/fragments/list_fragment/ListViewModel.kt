package com.example.movieapp.presentation.fragments.list_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.domain.model.ListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(val movieDao: MovieDao)  :ViewModel() {


    val listState = MutableStateFlow<List<ListItem>>(listOf())

    fun getList() {
        viewModelScope.launch {
            listState.update {
                movieDao.getListItems()
            }
        }
    }

}