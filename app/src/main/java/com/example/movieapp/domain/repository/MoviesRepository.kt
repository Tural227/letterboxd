package com.example.movieapp.domain.repository

import android.app.Person
import com.example.movieapp.domain.model.BackgroundItem
import com.example.movieapp.domain.model.DetailsItem
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.PersonDetailItem
import com.example.movieapp.domain.model.PersonItem
import com.example.movieapp.domain.model.SecondaryMovieItem
import com.example.movieapp.domain.model.UserReviewsItem


interface MoviesRepository {
    suspend fun getPopularMovies(page: Int): List<MovieItem>
    suspend fun getMovieDetails(id: Int): List<UserReviewsItem>
    suspend fun getSearchResults(movieName: String) : List<MovieItem>
    suspend fun getStackedPopularMovies(): List<List<SecondaryMovieItem>>
    suspend fun getBackgroundImages() : List<BackgroundItem>


    suspend fun getCrew(id: Int): List<PersonItem>
    suspend fun getCasts(id: Int): List<PersonItem>
    suspend fun getPersonDetail(personID : Int) : PersonDetailItem
    suspend fun getExactMovie(movieID: Int) : MovieItem
    suspend fun getRealPopularMovies(page: Int) : List<SecondaryMovieItem>
    suspend fun getDetails(movieID: Int) : DetailsItem
    suspend fun getMovieGenres() : List<String>











}