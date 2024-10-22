package com.example.movieapp.data.local

import androidx.room.TypeConverter
import com.example.movieapp.domain.model.MovieItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromGenresIDList(genresID: List<Int>): String {
        return Gson().toJson(genresID)
    }

    @TypeConverter
    fun toGenresIDList(genresIDString: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(genresIDString, listType)
    }
}