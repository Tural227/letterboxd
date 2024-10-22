package com.example.movieapp.presentation.fragments.recovery_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecoveryViewModel @Inject constructor(val authRepository: AuthRepository): ViewModel(){

    val isLoading = MutableStateFlow(false)
    val checkState = MutableStateFlow(false)

    fun resetPassword(string: String) {
        viewModelScope.launch {
            isLoading.update { true }
            checkState.update{authRepository.recoverAccount(string)}
            isLoading.update { false }
        }
    }

}