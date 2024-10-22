package com.example.movieapp.data.remote.repository

import com.example.movieapp.data.remote.myapi.MovieApi
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.UserReviewsItem
import com.example.movieapp.domain.repository.ReviewsRepository
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(val movieApi: MovieApi) : ReviewsRepository {
    override suspend fun getReviews(): List<UserReviewsItem> {

        val mainData: MutableList<UserReviewsItem> = mutableListOf()
        movieApi.getPopularMovies(1).results.map {top->
            movieApi.getReviews(top.id).results.map {
                mainData.add(
                    UserReviewsItem(
                        name = it.author,
                        review = it.content,
                        avatar = "https://image.tmdb.org/t/p/w200${it.authorDetails.avatarPath}",
                        rating = it.authorDetails.rating.toFloat(),
                        year = top.releaseDate,
                        moviePoster = "https://image.tmdb.org/t/p/w500${top.posterPath}",
                        movieName = top.title,
                        id = top.id,

                    )
                )
            }
        }
        return mainData.shuffled()
    }
}