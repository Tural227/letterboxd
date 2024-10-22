package com.example.movieapp.presentation.fragments.user_profile.background_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.databinding.BackgroundImageBinding
import com.example.movieapp.presentation.fragments.user_profile.background_fragment.adapters.BackgroundAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BackgroundFragment: BottomSheetDialogFragment() {
    var _binding : BackgroundImageBinding? = null
    val binding get() = _binding!!


    val viewModel : BackgroundFragmentViewModel by viewModels()

    val backgroundAdapter = BackgroundAdapter{
        lifecycleScope.launch {
            save("backPhoto",it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BackgroundImageBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backgroundRV.adapter = backgroundAdapter

        binding.exitButton.setOnClickListener {
            dismiss()
        }

        observer()

    }

    suspend fun save(key : String, value : String){
        val stringKey = stringPreferencesKey(key)
        viewModel.dataStore.edit {
            it[stringKey] = value
        }
        FancyToast.makeText(requireContext(),"Background image updated!",
            FancyToast.LENGTH_SHORT,
            FancyToast.INFO,false).show()

    }

    fun observer(){
        lifecycleScope.launch {
            viewModel.moviesState.filter { it.isNotEmpty() }.collectLatest {
                backgroundAdapter.updateAdapter(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}