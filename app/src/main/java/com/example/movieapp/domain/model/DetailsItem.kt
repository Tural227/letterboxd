package com.example.movieapp.domain.model

import com.example.movieapp.data.remote.model.exact_movie.Genre
import com.example.movieapp.data.remote.model.exact_movie.ProductionCompany
import com.example.movieapp.data.remote.model.exact_movie.ProductionCountry

data class DetailsItem (
    val budget : Long,
    val revenue : Long,
    val genres : List<Genre>,
    val companies : List<ProductionCompany>,
    val countries : List<ProductionCountry>
)