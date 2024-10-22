package com.example.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.domain.model.ListItem
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.RatingItem
import com.example.movieapp.domain.model.UserReviewsItem
import com.example.movieapp.domain.model.WatchlistItem


@Database([MovieItem::class,UserReviewsItem::class,WatchlistItem::class,ListItem::class, RatingItem::class],version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getMovieDao() : MovieDao
}