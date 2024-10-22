package com.example.movieapp.presentation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityHomeBinding
import com.example.movieapp.presentation.fragments.home_fragment.HomeFragment
import com.example.movieapp.presentation.fragments.search_fragment.MultiViewModel
import com.example.movieapp.presentation.fragments.search_fragment.SearchFragment
import com.example.movieapp.presentation.fragments.user_profile.UserProfileFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.fragment.app.activityViewModels
import com.google.android.material.navigation.NavigationBarView


@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController



        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navController
        )



    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}