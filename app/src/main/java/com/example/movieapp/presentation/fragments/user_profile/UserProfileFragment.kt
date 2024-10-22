package com.example.movieapp.presentation.fragments.user_profile



import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.common.data_store.copyUriToFile
import com.example.movieapp.databinding.UserProfileBinding
import com.example.movieapp.presentation.fragments.user_profile.adapters.FavouriteMoviesAdapter
import com.example.movieapp.presentation.fragments.user_profile.adapters.RatedMoviesAdapter
import com.example.movieapp.presentation.fragments.user_profile.adapters.UserReviewsAdapter
import com.shashank.sony.fancytoastlib.FancyToast


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class UserProfileFragment: BaseFragment<UserProfileBinding>(UserProfileBinding::inflate) {
    private val viewModel by viewModels<UserProfileViewModel>()


    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val favouriteMoviesAdapter = FavouriteMoviesAdapter{
        findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToMoviePageFragment(it))
    }
    private val ratedMoviesAdapter = RatedMoviesAdapter{
        findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToMoviePageFragment(it))
    }

    private val userReviewsAdapter = UserReviewsAdapter{
        findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToMoviePageFragment(it))
    }





    override fun main() {

        observer()
        setAdapters()
        setViewComponents()
        viewModel.userPreferences()


    }





    fun setAdapters(){
        binding.recyclerView.adapter = favouriteMoviesAdapter
        binding.recentRV.adapter = ratedMoviesAdapter
        binding.reviewsRV.adapter = userReviewsAdapter
    }

    override fun buttonListener() {
        super.buttonListener()
        binding.seeAllTextView.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToLikesFragment())
        }
        var flag = true
        binding.seeAllTextView2.setOnClickListener {
            if(flag){
                binding.seeAllTextView2.text = "See less"
                flag = false
            }
            else{
                binding.seeAllTextView2.text = "See all"
                flag = true
            }
            ratedMoviesAdapter.changeCollapse()
        }
        binding.seeAllTextView3.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToUserReviewsFragment())
        }

        binding.changeBackground.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val buttonPositive = dialog.findViewById<Button>(R.id.acceptButton)
            val buttonNegative = dialog.findViewById<Button>(R.id.cancelButton)
            val textView = dialog.findViewById<TextView>(R.id.dialogTextView)

            textView.text = "Change the profile background picture."

            buttonNegative.setOnClickListener {
                dialog.dismiss()
            }
            buttonPositive.setOnClickListener {
                findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToBackgroundFragment())
                dialog.dismiss()
            }

            dialog.show()
        }

    }



    fun observer(){
        lifecycleScope.launch {
            val key = stringPreferencesKey("backPhoto")
            dataStore.data
                .filterNotNull()
                .collectLatest {
                    val backPath = it[key]
                    if(backPath!=null) {
                        Glide.with(binding.backImageView).load(backPath).into(binding.backImageView)
                    }
            }
        }

        lifecycleScope.launch {
            val key = stringPreferencesKey("photoUri")
            dataStore.data
                .collectLatest {
                    val uri = it[key]
                    if(uri!=null){
                        binding.profileImageView.setImageURI(uri.toUri())
                        userReviewsAdapter.getImageUri(uri.toUri())
                    }

                }
        }
        val pickPhoto = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val destinationFile = File(context?.filesDir, "copied_photo.jpg")
                if(copyUriToFile(requireContext(),uri,destinationFile)){
                    Log.e("SALAM","SALMA")
                    val newUri = Uri.fromFile(destinationFile)
                    binding.profileImageView.setImageURI(newUri)
                    userReviewsAdapter.getImageUri(newUri)
                    lifecycleScope.launch{
                        save("photoUri",newUri.toString())
                        FancyToast.makeText(requireContext(),"Image successfully changed!",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show()
                    }
                    val fragmentManager = parentFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()


                    fragmentTransaction.detach(this@UserProfileFragment).commitNow()


                    fragmentTransaction.attach(this@UserProfileFragment).commitNow()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.userReviews.filter { it.isNotEmpty() }.collectLatest {
                if (it.isEmpty()){
                    binding.seeAllTextView2.text = "Add reviews"
                }else binding.seeAllTextView.text = "See all"
                if(it.size <= 3){
                    binding.seeAllTextView3.visibility = View.GONE
                }
                else{
                    binding.seeAllTextView3.visibility = View.VISIBLE
                }
                userReviewsAdapter.updateAdapter(it)
                binding.reviewCountTextView.text = it.size.toString()
            }

        }

        lifecycleScope.launch {
            viewModel.ratedMovies
                .filterNotNull()
                .collectLatest {
                    if (it.isEmpty()){
                        binding.seeAllTextView2.text = "Add movies"
                    }else binding.seeAllTextView.text = "See all"
                    if(it.size <= 5){
                        binding.seeAllTextView2.visibility = View.GONE
                    }
                    else{
                        binding.seeAllTextView2.visibility = View.VISIBLE
                    }
                    ratedMoviesAdapter.updateAdapter(it)
                }
        }

        lifecycleScope.launch {
            viewModel.favMovies
                .filter { it.isNotEmpty() }
                .collectLatest {
                    if (it.isEmpty()){
                        binding.seeAllTextView2.text = "Add movies"
                    }else binding.seeAllTextView.text = "See all"
                    if(it.size <= 5){
                        binding.seeAllTextView.visibility = View.GONE
                    }
                    else{
                        binding.seeAllTextView.visibility = View.VISIBLE
                    }
                    favouriteMoviesAdapter.updateAdapter(it)

            }
        }






        binding.changeImageView.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val buttonPositive = dialog.findViewById<Button>(R.id.acceptButton)
            val buttonNegative = dialog.findViewById<Button>(R.id.cancelButton)
            val textView = dialog.findViewById<TextView>(R.id.dialogTextView)

            textView.text = "Change the profile picture."

            buttonNegative.setOnClickListener {
                dialog.dismiss()
            }
            buttonPositive.setOnClickListener {
                pickPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    fun setViewComponents(){
        binding.userTextView.text = sharedPreferences.getString("user","Tural")
        lifecycleScope.launch {
            binding.watchlistCountTextView.text = viewModel.movieDao.getWatchlistItem().size.toString()
            binding.listCountTextView.text = viewModel.movieDao.getListItems().size.toString()
        }
    }


    suspend fun save(key: String, value : String){
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit {
            it[dataStoreKey] = value
        }

    }




}