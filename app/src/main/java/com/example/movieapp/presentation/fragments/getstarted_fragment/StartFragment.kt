package com.example.movieapp.presentation.fragments.getstarted_fragment

import androidx.navigation.fragment.findNavController
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.databinding.GetStartedBinding

class StartFragment: BaseFragment<GetStartedBinding>(GetStartedBinding::inflate) {


    override fun main() {

    }

    override fun buttonListener() {
        super.buttonListener()
        binding.button2.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToSignUpFragment())
        }
    }

}