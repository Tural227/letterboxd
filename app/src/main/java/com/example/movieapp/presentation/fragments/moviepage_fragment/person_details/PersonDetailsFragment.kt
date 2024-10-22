package com.example.movieapp.presentation.fragments.moviepage_fragment.person_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.PersonDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonDetailsFragment: BottomSheetDialogFragment() {
    val viewModel by viewModels<PersonDetailsViewModel>()

    val argument by navArgs<PersonDetailsFragmentArgs>()

    var _binding : PersonDetailBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PersonDetailBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewComponents()
        observer()




    }

    fun observer(){
        lifecycleScope.launch {
            viewModel.getDetails(argument.personID)
        }
    }

    fun setViewComponents(){
        lifecycleScope.launch {
            viewModel.detailsState
                .filterNotNull()
                .collectLatest {
                    Glide.with(binding.imageView19).load(it.profilePath).into(binding.imageView19)
                    binding.nameTextView.text = it.name
                    binding.textView45.text = it.biography
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}