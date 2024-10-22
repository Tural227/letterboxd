package com.example.movieapp.domain.repository

import com.example.movieapp.data.remote.model.movie_reviews.UserReviewsDto
import com.example.movieapp.domain.model.UserReviewsItem

interface ReviewsRepository {
    suspend fun getReviews() : List<UserReviewsItem>
}