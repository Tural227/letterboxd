package com.example.movieapp.presentation.fragments.home_fragment

import android.content.Intent
import android.content.SharedPreferences
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.data.local.MovieDao
import com.example.movieapp.databinding.HomePageBinding
import com.example.movieapp.presentation.activities.MainActivity
import com.example.movieapp.presentation.fragments.home_fragment.adapters.PopularMoviesAdapter
import com.example.movieapp.presentation.fragments.home_fragment.adapters.ReviewsAdapter
import com.example.movieapp.presentation.fragments.home_fragment.adapters.StackedMoviesAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment: BaseFragment<HomePageBinding>(HomePageBinding::inflate) { //tools context hissesini sorus
    val scope = CoroutineScope(Dispatchers.Main)



    private val viewModel by activityViewModels<HomeViewModel>() /////////////
    private val popularMoviesAdapter = PopularMoviesAdapter{
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMoviePageFragment(it))
    }

    private val stackedMoviesAdapter = StackedMoviesAdapter{navItem,position->
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBottomSheetFragment(
            navItem,position
        ))
    }
    private val reviewsAdapter = ReviewsAdapter{
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMoviePageFragment(it))
    }



    override fun main() {

        binding.helloTextView.text = Html.fromHtml("Hello, <font color = \"#E9A6A6\">${setName()}</font>!")
        observer()
        setAdapters()
        checkUserReviews()

        val drawerLayout = binding.drawerLayout
        val navigationView = binding.navView






        binding.sideViewButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }




        val header = navigationView.getHeaderView(0)
        val drawerButton = header.findViewById<ImageView>(R.id.drawerButton)





        val name = header.findViewById<TextView>(R.id.drawerNameTextView)
        name.text = setName()


        drawerButton.setOnClickListener {
            drawerLayout.closeDrawers()
        }

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_likes-> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLikesFragment())
                R.id.nav_watchlist -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWatchlistFragment())
                R.id.nav_list -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListFragment())
                R.id.nav_about -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserReviewsFragment())
                R.id.nav_logout -> {
                    viewModel.firebaseAuth.signOut()
                    Intent(requireContext(), MainActivity::class.java).also {intent->
                        startActivity(intent)
                        viewModel.sharedPreferences.edit {
                            putString("user",null)
                            putString("password",null)
                        }
                        requireActivity().finish()
                    }
                }
            }
            true
        }





    }


    fun checkUserReviews(){
        lifecycleScope.launch{
            val userReviews = viewModel.movieDao.getUserReviews()
            repeat(userReviews.size){
                reviewsAdapter.addUserReviews(userReviews[it])
            }
        }
    }



    fun setName(): String{
        return viewModel.sharedPreferences.getString("user","Tural")!!
    }


    fun observer(){
        scope.launch {
            val header = binding.navView.getHeaderView(0)
            val profilePicture = header.findViewById<ImageView>(R.id.headerPPImageView)
            val key = stringPreferencesKey("photoUri")
            viewModel.dataStore.data.collectLatest {
                val uri = it[key]
                if(uri!=null){
                    binding.profileImageView.setImageURI(uri.toUri())
                    profilePicture.setImageURI(uri.toUri())
                    reviewsAdapter.getImageUri(uri.toUri())
                }
            }
        }




        scope.launch {
            viewModel.moviesState
                .filterNotNull()
                .collect{
                    popularMoviesAdapter.updateAdapter(it)
                }
        }

        scope.launch {
            viewModel.topRatedState
                .filterNotNull()
                .collect{
                    stackedMoviesAdapter.updateAdapter(it)
                }
        }

        scope.launch {
            viewModel.reviewsState
                .filterNotNull()
                .collect{
                reviewsAdapter.updateAdapter(it)
            }
        }

        scope.launch {
            viewModel.isLoadingOne
                .collectLatest {
                    binding.progressBar3.isVisible = it
                }
        }

        scope.launch {
            viewModel.isLoadingTwo
                .collectLatest {
                    binding.progressBar4.isVisible = it
                }
        }
        scope.launch {
            viewModel.isLoadingThree
                .collectLatest {
                    binding.progressBar5.isVisible = it
                }
        }

    }

    fun setAdapters(){
        binding.popularFilmsRV.adapter = popularMoviesAdapter
        binding.popularListsRV.adapter = stackedMoviesAdapter
        binding.reviewsRV.adapter = reviewsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.coroutineContext.cancelChildren()
    }

}

