package com.example.movieapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity
data class MovieItem(
    @PrimaryKey(false) val id: Int,
    val director: String = "",
    val runtime :  Int = 0,
    val poster: String,
    val backPoster: String = "",
    val rating: Double,
    val movieName: String,
    val movieYear: String = "",
    val overview: String,
    val genresID : Int = 0,
    val genres : List<Int> = listOf()
)