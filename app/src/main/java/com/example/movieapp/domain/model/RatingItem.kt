package com.example.movieapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RatingItem (
    val poster : String,
    val rating : Float,
    @PrimaryKey(false) val  movieID : Int
)