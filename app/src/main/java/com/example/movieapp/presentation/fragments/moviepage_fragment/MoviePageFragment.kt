package com.example.movieapp.presentation.fragments.moviepage_fragment


import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.databinding.MoviePageBinding
import com.example.movieapp.domain.model.DetailsItem
import com.example.movieapp.domain.model.ListItem
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.WatchlistItem
import com.example.movieapp.presentation.fragments.moviepage_fragment.adapters.MovieReviewsAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.example.movieapp.presentation.fragments.moviepage_fragment.adapters.CastsAdapter
import com.example.movieapp.presentation.fragments.moviepage_fragment.adapters.CrewAdapter
import com.example.movieapp.presentation.fragments.moviepage_fragment.person_details.PersonDetailsFragment
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.last
import kotlin.random.Random

@AndroidEntryPoint
class MoviePageFragment: BaseFragment<MoviePageBinding>(MoviePageBinding::inflate) {

    val argument by navArgs<MoviePageFragmentArgs>()
    val viewModel by viewModels<MoviePageViewModel>()

    var movieItem : MovieItem? = null



    val castsAdapter = CastsAdapter{
        findNavController().navigate(MoviePageFragmentDirections.actionMoviePageFragmentToPersonDetailsFragment(it))
    }

    val crewAdapter = CrewAdapter{
        findNavController().navigate(MoviePageFragmentDirections.actionMoviePageFragmentToPersonDetailsFragment(it))
    }




    val movieReviewsAdapter = MovieReviewsAdapter()


    @Inject
    lateinit var movieDao: MovieDao

    @Inject
    lateinit var dataStore: DataStore<Preferences>



    override fun main() {


        viewModel.getMovieDetails(argument.movieID)

        lifecycleScope.launch {
            viewModel.movieState
                .filterNotNull()
                .collectLatest {movieItem->
                    checkUserReviews(movieItem)
                    customRating(movieItem)
                    setSpannableTextView(movieItem)
                    backUpButtonListener(movieItem)
                    setViewComponents(movieItem)
                    binding.rateOrReviewButton.setOnClickListener {

                        findNavController().navigate(
                            MoviePageFragmentDirections.actionMoviePageFragmentToReviewsPageFragment(
                                movieItem.id
                            )
                        )


                    }


            }
        }






        lifecycleScope.launch {
            viewModel.movieState.filterNotNull().collectLatest {
                movieItem = it
            }

        }

        detailsTabLayout()
        observer()
        setAdapters()
        checkChecker()
    }

    fun detailsTabLayout(){
        binding.tabLayout2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let{
                    when(tab.position){
                        0->{
                            binding.castRV.visibility = View.VISIBLE
                            binding.crewRV.visibility = View.GONE
                            binding.detailsCard.visibility = View.GONE
                        }
                        1->{
                            binding.castRV.visibility = View.GONE
                            binding.crewRV.visibility = View.VISIBLE
                            binding.detailsCard.visibility = View.GONE
                        }
                        2->{
                            binding.castRV.visibility = View.GONE
                            binding.crewRV.visibility = View.GONE
                            binding.detailsCard.visibility = View.VISIBLE
                        }
                    }
                }


            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

        })
    }





    fun setDetails(detailsItem: DetailsItem){
        binding.budgetTextView.text = "${detailsItem.budget}$"
        binding.revenueTextView.text = "${detailsItem.revenue}$"
        var genres = ""
        repeat(detailsItem.genres.size - 1){index->
            genres +="${detailsItem.genres[index].name}\n"
        }
        genres += detailsItem.genres.last().name
        binding.genreTextView.text = genres
        var companies = ""
        repeat(detailsItem.companies.size - 1){index->
            companies += "${detailsItem.companies[0].name}\n"
        }
        companies += detailsItem.companies.lastOrNull()?.name
        binding.companiesTextVeiw.text = companies
        var countries = ""
        repeat(detailsItem.countries.size - 1){index->
            countries += "${detailsItem.countries[index].name}\n"
        }
        countries += detailsItem.countries.lastOrNull()?.name
        binding.countriesTextView.text = countries

    }










    fun observer(){
        lifecycleScope.launch {
            val key = stringPreferencesKey("photoUri")
            dataStore.data.collectLatest {
                val uri = it[key]
                if(uri != null){
                    movieReviewsAdapter.getImageUri(uri.toUri())
                }
            }
        }


        lifecycleScope.launch {
            viewModel.detailsState
                .filterNotNull()
                .collectLatest {
                setDetails(it)
            }
        }

        lifecycleScope.launch {
            viewModel.reviewsState
                .collectLatest {
                    binding.noReviewTextView.isVisible = it.isEmpty()
                    movieReviewsAdapter.updateAdapter(it)
                }
        }

        lifecycleScope.launch {
            viewModel.castState
                .filter { it.isNotEmpty() }
                .collectLatest {
                    castsAdapter.updateAdapter(it)
            }
        }

        lifecycleScope.launch {
            viewModel.crewState
                .filter { it.isNotEmpty() }
                .collectLatest {
                    crewAdapter.updateAdapter(it)
            }
        }


    }
    fun setAdapters(){
        binding.reviewsRV.adapter = movieReviewsAdapter
        binding.castRV.adapter = castsAdapter
        binding.crewRV.adapter = crewAdapter
    }


    fun customRating (movieItem: MovieItem){
        val rating = movieItem.rating.toInt()
        val heightList = mutableListOf<Float>()
        repeat(5){
            if(it == rating - 1){
                heightList.add(
                    (Random.nextFloat() * 0.5f + 0.5f)
                )
            } else{
                heightList.add(
                    (Random.nextFloat() * 0.5f)

                )
            }
        }

        binding.customRating.data = heightList
    }



    fun checkUserReviews(movieItem: MovieItem){
        lifecycleScope.launch {
            val userReviews = movieDao.getUserReviews()
            repeat(userReviews.size){
                if(movieItem.id == userReviews[it].id){
                    movieReviewsAdapter.getUserReviews(userReviews[it])
                    binding.noReviewTextView.isVisible = false
                }
            }
        }
    }

    fun setSpannableTextView(movieItem: MovieItem){
        val text = "${movieItem.movieName} ${movieItem.movieYear}"

        val spannableString = SpannableString(text)

        val yearEnd = text.length
        val yearStart = yearEnd - 4

        spannableString.setSpan(
            RelativeSizeSpan(1f),
            0,
            yearStart-1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            RelativeSizeSpan(0.5f),
            yearStart,
            yearEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.movieNameTextView.text = spannableString

    }






    fun setViewComponents(movieItem: MovieItem){

        movieItem.let{
            Glide.with(binding.posterImageView).load(it.poster).into(binding.posterImageView)
            binding.runtimeTextView.text = "${it.runtime} mins"
            binding.directorTextView.text = "Directed by ${it.director}"

            binding.backPosterImageView.setImageWithGlide(it.backPoster)

            binding.contentTextView.text = it.overview
            binding.ratingTextView.text = it.rating.toString()
            binding.ratingBar5.rating = it.rating.toFloat()

        }

    }

    fun checkChecker(){
        lifecycleScope.launch {
            val listItems = movieDao.getListItems()
            val watchlistItems = movieDao.getWatchlistItem()
            repeat(listItems.size){
                if(listItems[it].movieID ==  argument.movieID){
                    binding.buttonAddList.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_check_24,0,0,0)
                    binding.buttonAddList.tag = "checked"
                }
            }

            repeat(watchlistItems.size){
                if(watchlistItems[it].movieID == argument.movieID){
                    binding.buttonAddWatchlist.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_check_24,0,0,0)
                    binding.buttonAddWatchlist.tag = "checked"
                }

            }
        }
    }


    fun backUpButtonListener(movieItem: MovieItem){
        binding.buttonAddList.setOnClickListener {
            lifecycleScope.launch {
                val listItem = ListItem(
                    movieID = movieItem.id,
                    rating = movieItem.rating,
                    poster = movieItem.poster,
                    movieName = movieItem.movieName
                )
                if(it.tag.toString() == "notChecked"){
                    binding.buttonAddList.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_check_24,0,0,0)
                    it.tag = "checked"
                    movieDao.addListItem(
                        listItem
                    )
                    FancyToast.makeText(requireContext(), "Added to lists!", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                }
                else{
                    it.tag = "notChecked"
                    binding.buttonAddList.setCompoundDrawablesWithIntrinsicBounds(R.drawable.buttontwo
                        ,0,0,0)
                    movieDao.deleteListItem(listItem.movieID)
                    FancyToast.makeText(requireContext(), "Removed from lists!", FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show()
                }
            }
        }



        binding.buttonAddWatchlist.setOnClickListener {
            lifecycleScope.launch {
                val watchlistItem = WatchlistItem(
                    movieID = movieItem.id,
                    rating = movieItem.rating,
                    poster = movieItem.poster,
                    movieName = movieItem.movieName
                )
                if(it.tag.toString() == "notChecked"){
                    binding.buttonAddWatchlist.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_check_24,0,0,0)
                    it.tag = "checked"
                    movieDao.addWatchlistItem(
                        watchlistItem
                    )
                    FancyToast.makeText(requireContext(), "Added to watchlist!", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                }
                else{
                    it.tag = "notChecked"
                    binding.buttonAddWatchlist.setCompoundDrawablesWithIntrinsicBounds(R.drawable.buttonthree
                        ,0,0,0)
                    movieDao.deleteWatchlistItem(watchlistItem.movieID)
                    FancyToast.makeText(requireContext(), "Removed from watchlist!", FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show()
                }
            }
        }



        binding.backwardButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }




}