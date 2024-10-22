package com.example.movieapp.presentation.fragments.login_fragment

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


//VIEW MODEL HEC VAXT DATA LAYERINI GORMEMELIDIR!!!!!!!!!!!!!!!!!!!!!!
@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseAuth: AuthRepository, val firebase: FirebaseAuth, val sharedPreferences: SharedPreferences,val fireStore: FirebaseFirestore, val context : Context): ViewModel() {


    val isLoading = MutableStateFlow(false)
    val checker = MutableStateFlow<Boolean?>(null)


    fun login(email: String, password: String){
        viewModelScope.launch {
            isLoading.update { true }
            checker.update{firebaseAuth.login(email,password)} // bu bize mesaj qaytardi
            isLoading.update { false }
        }

    }



}