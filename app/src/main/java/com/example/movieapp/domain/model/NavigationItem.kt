package com.example.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NavigationItem(
    val itemOne: SecondaryMovieItem,
    val itemTwo: SecondaryMovieItem,
    val itemThree: SecondaryMovieItem,
    val itemFour: SecondaryMovieItem
): Parcelable