package com.example.movieapp.presentation.fragments.moviepage_fragment.reviews_bottom_sheet_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.databinding.AllReviewsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AllReviewsBottomFragment: BottomSheetDialogFragment() {
    var _binding : AllReviewsBinding? = null
    val bindnig get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AllReviewsBinding.inflate(inflater,container,false)
        return bindnig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}