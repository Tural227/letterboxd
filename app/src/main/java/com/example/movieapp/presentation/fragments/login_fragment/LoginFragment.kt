package com.example.movieapp.presentation.fragments.login_fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.databinding.LogInBinding
import com.example.movieapp.presentation.activities.HomeActivity
import com.example.movieapp.presentation.activities.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: BaseFragment<LogInBinding>(LogInBinding::inflate) {
    private val viewModel by viewModels<LoginViewModel>()


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun main() {
        quickLogin()

    }

    private fun quickLogin(){
        if(viewModel.firebase.currentUser?.email != null){
            myIntent()
            requireActivity().finish()
        }

    }


    private fun myIntent(){


        Intent(requireContext(), HomeActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun buttonListener() {
        super.buttonListener()
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToStartFragment())
        }

        binding.loginButton.setOnClickListener {
            val username = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.login(username,password)

            lifecycleScope.launch {
                viewModel.checker
                    .filterNotNull()
                    .collectLatest {
                    if(it){
                        myIntent()
                        requireActivity().finish()
                    }
                }
            }

            lifecycleScope.launch {
                viewModel.isLoading.filterNotNull().collectLatest {
                    binding.view5.isVisible = it
                    binding.progressBar.isVisible = it
                }
            }


        }

        binding.forgotTextView.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRecoveryFragment())
        }

        binding.root.setOnClickListener {
            hideKeyboard(it)
        }

    }


    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}