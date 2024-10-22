package com.example.movieapp.domain.repository



interface AuthRepository {
    suspend fun login(email : String, password: String): Boolean
    suspend fun register(email: String,name: String ,password: String): Boolean
    suspend fun recoverAccount(email: String) : Boolean
}