package com.example.movieapp.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavouriteEntity (
    val posterPath: String,
     @PrimaryKey(false) val movieID: Int
)