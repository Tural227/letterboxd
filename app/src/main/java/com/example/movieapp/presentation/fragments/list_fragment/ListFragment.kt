package com.example.movieapp.presentation.fragments.list_fragment

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
import com.example.movieapp.databinding.ListLayoutBinding
import com.example.movieapp.presentation.fragments.list_fragment.adapters.ListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment: BaseFragment<ListLayoutBinding>(ListLayoutBinding::inflate) {
    val viewModel by viewModels<ListViewModel>()
    val listAdapter = ListAdapter(
        onClick={
        findNavController().navigate(ListFragmentDirections.actionListFragmentToMoviePageFragment(it))

        },
        onLongClick = {position,ID->
            showDialog(position,ID)
        })

    override fun main() {
        observer()
        binding.listRV.adapter = listAdapter
        viewModel.getList()

    }
    override fun buttonListener() {
        super.buttonListener()
        binding.backwardButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    fun observer(){
        lifecycleScope.launch {
            viewModel.listState
                .collectLatest {
                    binding.emptyTextView2.isVisible = it.isEmpty()
                    listAdapter.updateAdapter(it)
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
            listAdapter.deleteMovie(position)
            lifecycleScope.launch {
                viewModel.movieDao.deleteListItem(id)
                viewModel.getList()
            }

            dialog.dismiss()
        }

        buttonNegative.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}