package com.example.movieapp.data.remote.repository

import com.example.movieapp.data.remote.myapi.MovieApi
import com.example.movieapp.domain.model.BackgroundItem
import com.example.movieapp.domain.model.DetailsItem
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.PersonDetailItem
import com.example.movieapp.domain.model.PersonItem
import com.example.movieapp.domain.model.SecondaryMovieItem
import com.example.movieapp.domain.model.UserReviewsItem
import com.example.movieapp.domain.repository.MoviesRepository
import javax.inject.Inject
import kotlin.math.roundToInt


class MoviesRepositoryImpl @Inject constructor(private val movieApi: MovieApi): MoviesRepository {
    override suspend fun getPopularMovies(page: Int): List<MovieItem> {

        return movieApi.getPopularMovies(page).results.map {
            MovieItem(
                genresID = it.genreIds[0],
                poster = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                rating = it.voteAverage,
                movieName = it.title,
                overview = it.overview,
                id = it.id,
                genres = it.genreIds
                )
        }
    }






    override suspend fun getMovieDetails(id: Int): List<UserReviewsItem> {

        return movieApi.getReviews(id).results.map {
            UserReviewsItem(
                name = it.author,
                review = it.content,
                avatar = "https://image.tmdb.org/t/p/w200${it.authorDetails.avatarPath}",
                rating = it.authorDetails.rating.toFloat()
            )
        }
    }


    override suspend fun getDetails(movieID: Int) : DetailsItem{
        movieApi.getExactMovie(movieID).also {
            return DetailsItem(
                budget = it.budget.toLong(),
                revenue = it.revenue.toLong(),
                genres = it.genres,
                companies = it.productionCompanies,
                countries = it.productionCountries
            )
        }
    }

    override suspend fun getMovieGenres(): List<String> {
        return movieApi.getMovieGenres().genres.map {
            it.name
        }


    }


    override suspend fun getCasts(id: Int): List<PersonItem> {
        return movieApi.getCredits(id).cast.map{
            PersonItem(
                profilePath = "https://image.tmdb.org/t/p/w200${it.profilePath}",
                name = it.name,
                personID = it.id
            )
        }
    }

    override suspend fun getCrew(id: Int): List<PersonItem> {
        return movieApi.getCredits(id).crew.map{
            PersonItem(
                profilePath = "https://image.tmdb.org/t/p/w200${it.profilePath}",
                name = it.name,
                personID = it.id
            )
        }
    }

    override suspend fun getSearchResults(movieName: String): List<MovieItem> {

        return movieApi.getSearchResult(movieName).results.map {
            MovieItem(
                poster = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                rating = it.voteAverage,
                movieName = it.title,
                overview = it.overview,
                id = it.id,
            )
        }
    }

    override suspend fun getStackedPopularMovies(): List<List<SecondaryMovieItem>> {

        val movies = movieApi.getPopularMovies(2).results.map {
            SecondaryMovieItem(
                moviePoster = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                movieID = it.id
            )
        }

        val finalMovies = mutableListOf<List<SecondaryMovieItem>>()

        repeat(movies.size/4){
            val subList = movies.subList(it*4,it*4+4)
            finalMovies.add(subList)
        }
        return finalMovies
    }

    override suspend fun getBackgroundImages(): List<BackgroundItem> {
        return movieApi.getPopularMovies(1).results.map {
            BackgroundItem(
                backPoster = "https://image.tmdb.org/t/p/w500${it.backdropPath}",
                movieName = it.title
            )
        }
    }

    override suspend fun getPersonDetail(personID: Int): PersonDetailItem {
        val personDetail = movieApi.getPersonDetail(personID)
        return PersonDetailItem(
            name = personDetail.name,
            biography = personDetail.biography,
            profilePath = "https://image.tmdb.org/t/p/w200${personDetail.profilePath}"
        )
    }

    override suspend fun getExactMovie(movieID: Int): MovieItem {
        val movieDirector = movieApi.getCredits(movieID).crew.first().name
        movieApi.getExactMovie(movieID).also {
            return MovieItem(
                id = it.id,
                runtime = it.runtime,
                director = movieDirector,
                poster = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                backPoster = "https://image.tmdb.org/t/p/w500${it.backdropPath}",
                rating = ((it.voteAverage*10/2).roundToInt())/10.0,
                movieName = it.title,
                movieYear = it.releaseDate.split('-').first(),
                overview = it.overview
            )
        }



    }

    override suspend fun getRealPopularMovies(page: Int): List<SecondaryMovieItem> {
        return movieApi.getPopularMovies(page).results.map {
            SecondaryMovieItem(
                moviePoster = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                movieID = it.id
            )
        }
    }


}