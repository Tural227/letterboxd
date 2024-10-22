package com.example.movieapp.presentation.fragments.user_reviews_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.domain.model.UserReviewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserReviewsViewModel @Inject constructor(val movieDao: MovieDao):ViewModel() {




    val reviewsState = MutableStateFlow<List<UserReviewsItem>>(listOf())



    fun getReviews(){
        viewModelScope.launch {
            reviewsState.update {
                movieDao.getUserReviews()
            }
        }
    }






}