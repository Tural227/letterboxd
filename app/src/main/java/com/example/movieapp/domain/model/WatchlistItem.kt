package com.example.movieapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WatchlistItem (
    @PrimaryKey(false) val movieID : Int,
    val movieName: String,
    val poster: String,
    val rating: Double
)