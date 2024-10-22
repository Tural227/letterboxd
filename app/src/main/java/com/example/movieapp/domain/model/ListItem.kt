package com.example.movieapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ListItem (
    @PrimaryKey(false) val movieID : Int,
    val poster : String,
    val rating : Double,
    val movieName: String,
)