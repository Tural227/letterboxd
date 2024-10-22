package com.example.movieapp.presentation.fragments.moviepage_fragment.tab_layout.fragments


import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.databinding.DetailsScreenBinding
import com.example.movieapp.presentation.fragments.moviepage_fragment.tab_layout.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailsScreenFragment: BaseFragment<DetailsScreenBinding>(DetailsScreenBinding::inflate) {


    override fun main() {

    }




}