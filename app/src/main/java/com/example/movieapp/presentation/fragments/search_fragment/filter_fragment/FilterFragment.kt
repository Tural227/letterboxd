package com.example.movieapp.presentation.fragments.search_fragment.filter_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.movieapp.R
import com.example.movieapp.databinding.FilterLayoutBinding
import com.example.movieapp.presentation.fragments.search_fragment.MultiViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FilterFragment:BottomSheetDialogFragment() {
    var _binding : FilterLayoutBinding? = null
    val binding get() = _binding!!
    val viewModel by viewModels<FilterViewModel>()


    val multiViewModel by activityViewModels<MultiViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCustomSpinner()
        lifecycleScope.launch {
            multiViewModel.genreState.filterNotNull()
                .collectLatest {
                    binding.autoCompleteTextView.setText(it)
                }
        }

        binding.applyButton.setOnClickListener {
            multiViewModel.selectGenre(binding.autoCompleteTextView.text.toString())

            lifecycleScope.launch {
                multiViewModel.checkClear.update { true }
            }
            dismiss()
        }


        binding.exitButton.setOnClickListener {
            dismiss()
        }

        binding.clearButton.setOnClickListener {
            lifecycleScope.launch{
                multiViewModel.selectGenre("Select a genre")

            }
            lifecycleScope.launch {
                multiViewModel.checkClear.update { true }
            }

            dismiss()
        }

    }
    fun setCustomSpinner(){
        lifecycleScope.launch {
            viewModel.genresState.collectLatest {
                ArrayAdapter(requireContext(), R.layout.spinner_item, it).also {
                    binding.autoCompleteTextView.setAdapter(it)
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }






}