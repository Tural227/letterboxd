package com.example.movieapp.presentation.fragments.reviewspage_fragment


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.data.local.AppDatabase
import com.example.movieapp.data.local.FavouriteEntity
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.data.remote.myapi.MovieApi
import com.example.movieapp.databinding.ReviewsPageBinding
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.RatingItem
import com.example.movieapp.domain.model.UserReviewsItem
import com.example.movieapp.domain.repository.MoviesRepository
import com.example.movieapp.presentation.fragments.search_fragment.MultiViewModel
import com.example.movieapp.presentation.fragments.user_profile.adapters.UserReviewsAdapter

import com.google.android.material.datepicker.MaterialDatePicker
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ReviewsPageFragment: BaseFragment<ReviewsPageBinding>(ReviewsPageBinding::inflate) {

    val argument by navArgs<ReviewsPageFragmentArgs>()

    val viewModel by viewModels<ReviewViewModel>()





    @Inject
    lateinit var sharedPref: SharedPreferences



    @RequiresApi(Build.VERSION_CODES.O)
    override fun main() {
        lifecycleScope.launch {
            viewModel.getMovie(argument.movieID)
        }
        lifecycleScope.launch {
            viewModel.exactMovie.filterNotNull().collectLatest {

                buttonListeners(it)
                funnyButton(it)
                setViewComponents(it)
                checkHeart(it)
            }

        }



    }

    fun funnyButton(movieItem: MovieItem){
        binding.publishButton.setOnClickListener {
            if (binding.editTextTextMultiLine4.text.toString() != "" && binding.scaleRatingBar.rating != 0f) {
                lifecycleScope.launch {
                    viewModel.movieDao.addUserReview(
                        UserReviewsItem(
                            movieName = movieItem.movieName,
                            year = movieItem.movieYear,
                            id = movieItem.id,
                            name = sharedPref.getString("user", "Tural")!!,
                            review = binding.editTextTextMultiLine4.text.toString(),
                            avatar = "",
                            moviePoster = movieItem.poster,
                            rating = binding.scaleRatingBar.rating*2,
                            date = binding.textView37.text.toString()
                        )
                    )
                }
                if(binding.scaleRatingBar.rating != 0f){

                    lifecycleScope.launch {
                        val item : RatingItem?= viewModel.movieDao.getRatedItem(movieItem.id)
                        if(binding.scaleRatingBar.rating != item?.rating){
                            viewModel.movieDao.deleteRatedMovie(movieItem.id)
                            viewModel.movieDao.addRatedMovie(
                                RatingItem(
                                    poster = movieItem.poster,
                                    rating = binding.scaleRatingBar.rating,
                                    movieID = movieItem.id
                                )
                            )
                        }
                    }
                }
                FancyToast.makeText(requireContext(),"Review submitted successfully!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                findNavController().popBackStack()
            }
            else if(binding.editTextTextMultiLine4.text.toString() == ""){
                FancyToast.makeText(requireContext(),"Please write a review before submitting!",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
            }
            else{
                FancyToast.makeText(requireContext(), "Please rate before submitting!", FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setViewComponents(movieItem: MovieItem) {
        Glide.with(this).load(movieItem.poster).into(binding.posterImageView)
        binding.moiveNameTextView.text = movieItem.movieName
        binding.yearTextView.text = movieItem.movieYear


        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val formattedDate = date.format(formatter)
        binding.textView37.text = formattedDate

    }

    fun checkHeart(movieItem: MovieItem)
    {

        lifecycleScope.launch {
            val favMovies = viewModel.movieDao.getFavs()
            repeat(favMovies.size) {
                if (favMovies[it].id == movieItem.id) {
                    binding.saveButton.setImageResource(R.drawable.savelikered)
                    binding.saveButton.tag = "Favourite"
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun datePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select a date")
        val datePicker = builder.build()
        datePicker.addOnPositiveButtonClickListener { selection ->
            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(selection))
            binding.textView37.text = date
        }
        datePicker.show(requireActivity().supportFragmentManager, "")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buttonListeners(movieItem: MovieItem) {
        super.buttonListener()
        binding.backwardButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonDatePicker.setOnClickListener {
            datePicker()
        }

        binding.saveButton.setOnClickListener {
            if (binding.saveButton.tag == "notFavourite") {
                binding.saveButton.setImageResource(R.drawable.savelikered)
                binding.saveButton.tag = "Favourite"

                lifecycleScope.launch {
                    viewModel.movieDao.addFavourite(movieItem)
                }

                FancyToast.makeText(requireContext(),"Added to your likes!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()

            } else {
                binding.saveButton.setImageResource(R.drawable.savelike)
                binding.saveButton.tag = "notFavourite"
                lifecycleScope.launch {
                    viewModel.movieDao.deleteFavourite(argument.movieID)
                }
                FancyToast.makeText(requireContext(),"Removed from your likes!",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show()
            }
        }

        binding.root.setOnClickListener {
            hideKeyboard(it)
        }
    }

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}