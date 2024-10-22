package com.example.movieapp.di

import com.example.movieapp.data.remote.myapi.MovieApi
import com.example.movieapp.data.remote.repository.MoviesRepositoryImpl
import com.example.movieapp.data.remote.repository.ReviewsRepositoryImpl
import com.example.movieapp.domain.repository.MoviesRepository
import com.example.movieapp.domain.repository.ReviewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun getApi(): MovieApi{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun getPopularMovies(movieApi: MovieApi): MoviesRepository{
        return MoviesRepositoryImpl(movieApi)
    }

    @Provides
    @Singleton
    fun getUserReviews(movieApi: MovieApi): ReviewsRepository{
        return ReviewsRepositoryImpl(movieApi)
    }


}