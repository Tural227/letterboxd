package com.example.movieapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class UserReviewsItem(
    @PrimaryKey(true) val databaseId: Int = 0,


    val id: Int = 0,
    val name : String,
    val review : String,
    val avatar : String,
    val rating : Float,
    val year : String = "",
    val movieName : String  = "",
    val moviePoster : String = "",
    val date : String = ""
): Parcelable