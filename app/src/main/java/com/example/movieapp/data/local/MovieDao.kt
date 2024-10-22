package com.example.movieapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.domain.model.ListItem
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.RatingItem
import com.example.movieapp.domain.model.UserReviewsItem
import com.example.movieapp.domain.model.WatchlistItem


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavourite(movieItem: MovieItem)

    @Query("SELECT * from MovieItem")
    suspend fun getFavs(): List<MovieItem>

    @Query("DELETE FROM MOVIEITEM WHERE id = :movieID")
    suspend fun deleteFavourite(movieID: Int)


    @Insert
    suspend fun addUserReview(userReviewsItem: UserReviewsItem)

    @Query("SELECT * from USERREVIEWSITEM")
    suspend fun getUserReviews(): List<UserReviewsItem>

    @Query("DELETE from userreviewsitem where databaseId = :movieID")
    suspend fun deleteUserReview(movieID: Int)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addListItem(listItem: ListItem)

    @Query("SELECT * FROM LISTITEM")
    suspend fun getListItems() : List<ListItem>

    @Query("DELETE FROM listItem WHERE movieID = :listItemID")
    suspend fun deleteListItem(listItemID: Int)



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWatchlistItem(watchlistItem: WatchlistItem)

    @Query("SELECT * FROM WatchlistItem")
    suspend fun getWatchlistItem() : List<WatchlistItem>

    @Query("DELETE FROM WatchlistItem WHERE movieID = :watchlistItemID")
    suspend fun deleteWatchlistItem(watchlistItemID: Int)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRatedMovie(ratingItem: RatingItem)

    @Query("SELECT * FROM RATINGITEM")
    suspend fun getRatedMovies() : List<RatingItem>

    @Query("DELETE from RATINGITEM where movieID= :ID")
    suspend fun deleteRatedMovie(ID: Int)

    @Query("SELECT * FROM RATINGITEM WHERE movieID = :ID")
    suspend fun getRatedItem(ID : Int): RatingItem

}