package com.example.movieapp.presentation.fragments.signup_fragment

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class SignUpViewModel @Inject constructor(private val firebaseAuth: AuthRepository, val sharedPreferences: SharedPreferences, private val context: Context): ViewModel() {

    val isLoading = MutableStateFlow(false)
    val message = MutableStateFlow<Boolean?>(null)

    fun register(username: String, user: String, password: String){
        if(user.isEmpty() && username.isEmpty() && password.isEmpty()){
            FancyToast.makeText(context,"Register Failed: Fill in the gaps!", FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show()
        }
        else{
            sharedPreferences.edit {
                this.putString("user",user)
            }
            viewModelScope.launch {
                isLoading.update{ true }
                message.update { firebaseAuth.register(username, user, password) }
                isLoading.update { false }
            }

        }
    }

}