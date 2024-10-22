package com.example.movieapp.data.remote.model.movie_genre


import com.google.gson.annotations.SerializedName

data class MovieGenreDto(
    @SerializedName("genres")
    val genres: List<Genre>
)