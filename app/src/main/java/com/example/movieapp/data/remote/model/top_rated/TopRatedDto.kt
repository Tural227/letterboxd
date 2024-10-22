package com.example.movieapp.data.remote.model.top_rated


import com.google.gson.annotations.SerializedName

data class TopRatedDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)