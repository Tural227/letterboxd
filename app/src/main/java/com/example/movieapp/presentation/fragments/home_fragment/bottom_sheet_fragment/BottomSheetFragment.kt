package com.example.movieapp.presentation.fragments.home_fragment.bottom_sheet_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class BottomSheetFragment: BottomSheetDialogFragment() {
    private var _binding : BottomSheetBinding? = null
    private val binding get() = _binding!!

    private val arguments by navArgs<BottomSheetFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(arguments){
            binding.textView27.text = "List $position"
            Glide.with(binding.imageView7).load(itemStacked.itemOne.moviePoster).into(binding.imageView7)
            Glide.with(binding.imageView8).load(itemStacked.itemTwo.moviePoster).into(binding.imageView8)
            Glide.with(binding.imageView11).load(itemStacked.itemThree.moviePoster).into(binding.imageView11)
            Glide.with(binding.imageView13).load(itemStacked.itemFour.moviePoster).into(binding.imageView13)
        }
        buttonListener()

    }

    fun buttonListener(){
        binding.imageView7.setOnClickListener {
            findNavController().navigate(BottomSheetFragmentDirections.actionBottomSheetFragmentToMoviePageFragment(arguments.itemStacked.itemOne.movieID))
        }
        binding.imageView8.setOnClickListener {
            findNavController().navigate(BottomSheetFragmentDirections.actionBottomSheetFragmentToMoviePageFragment(arguments.itemStacked.itemTwo.movieID))
        }
        binding.imageView11.setOnClickListener {
            findNavController().navigate(BottomSheetFragmentDirections.actionBottomSheetFragmentToMoviePageFragment(arguments.itemStacked.itemThree.movieID))
        }
        binding.imageView13.setOnClickListener {
            findNavController().navigate(BottomSheetFragmentDirections.actionBottomSheetFragmentToMoviePageFragment(arguments.itemStacked.itemFour.movieID))
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}