package com.example.movieapp.presentation.fragments.likes_fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.databinding.LikesLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LikesFragment:BaseFragment<LikesLayoutBinding>(LikesLayoutBinding::inflate) {
    val viewModel by viewModels<LikesViewModel>()
    val likesAdapter = LikesAdapter(
        onClick = {
        findNavController().navigate(LikesFragmentDirections.actionLikesFragmentToMoviePageFragment(it))
        },
        onLongClick = {position,id->
            showDialog(position, id)
        }
        )

    override fun main() {
        binding.watchlistRV.adapter = likesAdapter
        observer()
        binding.backwardButton.setOnClickListener {
            findNavController().navigateUp()
        }
        viewModel.getLikes()
    }
    fun observer(){
        lifecycleScope.launch {
            viewModel.likesState.collectLatest {
                binding.emptyTextView2.isVisible = it.isEmpty()
                likesAdapter.updateAdapter(it)
            }

        }
    }

    fun showDialog(position: Int, id :Int){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_layout)
        val buttonPositive = dialog.findViewById<Button>(R.id.acceptButton)
        val buttonNegative = dialog.findViewById<Button>(R.id.cancelButton)
        val textView = dialog.findViewById<TextView>(R.id.dialogTextView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        textView.text = "Remove selected movie."
        buttonPositive.text = "Remove"
        buttonPositive.setOnClickListener {
            likesAdapter.deleteMovie(position)
            lifecycleScope.launch {
                viewModel.movieDao.deleteFavourite(id)
                viewModel.getLikes()
            }

            dialog.dismiss()
        }

        buttonNegative.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}