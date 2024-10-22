package com.example.movieapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.movieapp.data.local.AppDatabase
import com.example.movieapp.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    @Singleton
    fun getSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("myDataBase", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun getRoomDatabase(context: Context): MovieDao {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,"my_database"
        ).build().getMovieDao()
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

    @Provides
    @Singleton
    fun geMyDataStore(context: Context) : DataStore<Preferences>{
        return context.dataStore
    }

}