package com.example.movieapp.presentation.fragments.recovery_fragment


import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.databinding.RecoveryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecoveryFragment : BaseFragment<RecoveryFragmentBinding>(RecoveryFragmentBinding::inflate) {

    val viewModel by viewModels<RecoveryViewModel>()

    override fun main() {
        observer()
    }

    override fun buttonListener() {
        super.buttonListener()
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(RecoveryFragmentDirections.actionRecoveryFragmentToStartFragment())
        }

        binding.resetButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            viewModel.resetPassword(email)
        }

        binding.root.setOnClickListener {
            hideKeyboard(it)
        }

    }

    fun observer(){

        lifecycleScope.launch {
            viewModel.checkState.collectLatest {
                if(it){
                    findNavController().popBackStack()

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

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}