package com.example.movieapp.data.remote.myapi

import com.example.movieapp.data.remote.model.exact_movie.ExactMovieDto
import com.example.movieapp.data.remote.model.movie_credits.CreditsDto
import com.example.movieapp.data.remote.model.movie_genre.MovieGenreDto
import com.example.movieapp.data.remote.model.movie_reviews.AuthorDetails
import com.example.movieapp.data.remote.model.movie_reviews.UserReviewsDto
import com.example.movieapp.data.remote.model.person_detail.PersonDetailDto
import com.example.movieapp.data.remote.model.popular.PopularMoviesDto
import com.example.movieapp.data.remote.model.search.SearchResultDto
import com.example.movieapp.data.remote.model.top_rated.TopRatedDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {
    @GET("movie/popular?api_key=6d022c1eb5598357305a9546ff27b61b")
    suspend fun getPopularMovies(@Query("page") page: Int): PopularMoviesDto

    @GET("movie/{id}/reviews?api_key=6d022c1eb5598357305a9546ff27b61b")
    suspend fun getReviews(@Path("id") movieID: Int?): UserReviewsDto

    @GET("movie/{id}/credits?api_key=6d022c1eb5598357305a9546ff27b61b")
    suspend fun getCredits(@Path("id") movieID: Int?): CreditsDto

    @GET("search/movie?api_key=6d022c1eb5598357305a9546ff27b61b")
    suspend fun getSearchResult(@Query("query") movie : String) : SearchResultDto

    @GET("movie/{id}?api_key=6d022c1eb5598357305a9546ff27b61b")
    suspend fun getExactMovie(@Path("id") movieID: Int) : ExactMovieDto

    @GET("person/{personID}?api_key=6d022c1eb5598357305a9546ff27b61b")
    suspend fun getPersonDetail(@Path ("personID") personID : Int) : PersonDetailDto

    @GET("genre/movie/list?api_key=6d022c1eb5598357305a9546ff27b61b")
    suspend fun getMovieGenres() : MovieGenreDto


}