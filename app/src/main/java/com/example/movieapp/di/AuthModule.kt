package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.data.remote.repository.AuthRepositoryImpl
import com.example.movieapp.domain.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    fun getFirebase() : FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun getAuth(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore, context : Context): AuthRepository = AuthRepositoryImpl(firebaseAuth, firestore, context)

    @Provides
    @Singleton
    fun getFirestore(): FirebaseFirestore = Firebase.firestore
}