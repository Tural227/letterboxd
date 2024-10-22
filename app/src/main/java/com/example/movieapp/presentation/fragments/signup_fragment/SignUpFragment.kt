package com.example.movieapp.presentation.fragments.signup_fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.databinding.SignUpBinding
import com.example.movieapp.presentation.activities.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpBinding>(SignUpBinding::inflate) {
    private val viewModel by viewModels<SignUpViewModel>()



    override fun main() {

    }




    private fun myIntent(){
        Intent(requireContext(), HomeActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun buttonListener() {
        super.buttonListener()
        binding.buttonSignUp.setOnClickListener {
            val user = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.register(email,user,password)

            viewModel.sharedPreferences.edit {
                this.putString("user",user)
            }

            lifecycleScope.launch {
                viewModel.message
                    .filterNotNull()
                    .collectLatest {
                        if(it){
                            myIntent()
                            requireActivity().finish()
                        }
                    }
            }
            lifecycleScope.launch {
                viewModel.isLoading.collectLatest {
                    binding.view6.isVisible = it
                    binding.progressBar2.isVisible = it
                }
            }

        }

        binding.loginPageTextView.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
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