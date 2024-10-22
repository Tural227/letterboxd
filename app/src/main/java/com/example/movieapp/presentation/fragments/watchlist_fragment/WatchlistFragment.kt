package com.example.movieapp.presentation.fragments.watchlist_fragment



import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.databinding.WatchlistLayoutBinding
import com.example.movieapp.presentation.fragments.watchlist_fragment.adapters.WatchlistAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchlistFragment: BaseFragment<WatchlistLayoutBinding>(WatchlistLayoutBinding::inflate) {
    val viewModel by viewModels<WatchlistViewModel>()

    val watchlistAdapter = WatchlistAdapter(
        onClick = {
        findNavController().navigate(WatchlistFragmentDirections.actionWatchlistFragmentToMoviePageFragment(it))
        },
        onLongClick = {position,ID->
            showDialog(position,ID)

        }
        )

    override fun main() {
        observer()
        viewModel.getWatchlist()
        binding.watchlistRV.adapter = watchlistAdapter
        lifecycleScope.launch {

            viewModel.watchListState

                .collectLatest {
                    Log.e("HAHAHA",it.toString())
                    binding.emptyTextView2.isVisible = it.isEmpty()
                    watchlistAdapter.updateAdapter(it)
                 }
        }
    }

    override fun buttonListener() {
        super.buttonListener()
        binding.backwardButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun observer(){
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
            watchlistAdapter.deleteMovie(position)
            lifecycleScope.launch {
                viewModel.movieDao.deleteWatchlistItem(id)
                viewModel.getWatchlist()
            }

            dialog.dismiss()
        }

        buttonNegative.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}