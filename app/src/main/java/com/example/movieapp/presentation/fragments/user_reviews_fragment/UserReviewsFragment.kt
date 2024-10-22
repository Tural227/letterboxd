package com.example.movieapp.presentation.fragments.user_reviews_fragment


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.databinding.ReviewsLayoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserReviewsFragment : BaseFragment<ReviewsLayoutBinding>(ReviewsLayoutBinding::inflate) {
    val viewModel by viewModels<UserReviewsViewModel>()

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    val reviewsAdapter = ReviewsAdapter(
        onClick = {
        findNavController().navigate(UserReviewsFragmentDirections.actionUserReviewsFragmentToMoviePageFragment(it))
        },
        onLongClick = {movieID, position->
            showDialog(position,movieID)
        }
    )




    override fun main() {
        observer()
        binding.reviewsRV.adapter = reviewsAdapter
        viewModel.getReviews()




    }

    override fun buttonListener() {
        super.buttonListener()
        binding.backwardButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    fun observer(){
        lifecycleScope.launch {
            val key = stringPreferencesKey("photoUri")
            dataStore.data.collectLatest {
                val uri = it[key]
                if(uri!=null){
                    reviewsAdapter.getImageUri(uri.toUri())
                }

            }
        }

        lifecycleScope.launch {
            viewModel.reviewsState
                .collectLatest {
                    binding.emptyTextView.isVisible = it.isEmpty()
                    reviewsAdapter.updateAdapter(it)
                }
        }
    }
    fun showDialog(position : Int, id :Int){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_layout)
        val buttonPositive = dialog.findViewById<Button>(R.id.acceptButton)
        val buttonNegative = dialog.findViewById<Button>(R.id.cancelButton)
        val textView = dialog.findViewById<TextView>(R.id.dialogTextView)
        textView.text = "Remove selected review."
        buttonPositive.text = "Remove"
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        buttonPositive.setOnClickListener {
            reviewsAdapter.deleteReview(position)
            lifecycleScope.launch {
                viewModel.movieDao.deleteUserReview(id)
                viewModel.getReviews()
            }

            dialog.dismiss()
        }

        buttonNegative.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

}