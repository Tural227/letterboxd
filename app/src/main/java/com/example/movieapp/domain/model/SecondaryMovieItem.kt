package com.example.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SecondaryMovieItem (
    val moviePoster: String,
    val movieID: Int
): Parcelable