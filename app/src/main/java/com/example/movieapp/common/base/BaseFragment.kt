package com.example.movieapp.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>(val inflate: (LayoutInflater,ViewGroup?,Boolean)-> VB): Fragment() {
    private var _binding : VB? = null
    protected val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater,container,false)
        return binding.root
    }

    abstract fun main()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main()
        buttonListener()
    }

    open fun buttonListener(){}


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}