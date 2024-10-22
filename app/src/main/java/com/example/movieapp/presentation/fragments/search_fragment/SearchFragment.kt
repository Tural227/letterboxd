package com.example.movieapp.presentation.fragments.search_fragment


import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.SurfaceControl.Transaction
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseFragment
import com.example.movieapp.databinding.SearchFragmentBinding
import com.example.movieapp.presentation.fragments.search_fragment.adapters.ExploreAdapter
import com.example.movieapp.presentation.fragments.search_fragment.adapters.SearchResultAdapter
import com.example.movieapp.presentation.fragments.search_fragment.filter_fragment.FilterFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment:BaseFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate) {


    @Inject
    lateinit var dataStore: DataStore<Preferences>

    val exploreAdapter = ExploreAdapter{
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToMoviePageFragment(it)
        )
    }
    val searchResultAdapter = SearchResultAdapter {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToMoviePageFragment(
                it
            )
        )
    }




    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }



    val viewModel by activityViewModels<SearchViewModel>()

    val multiViewModel by activityViewModels<MultiViewModel>()





    override fun main() {



        lifecycleScope.launch{
            viewModel.genreState
                .filter { it.isNotEmpty() }
                .collectLatest {
                    exploreAdapter.genre = it

                }
        }



        lifecycleScope.launch {
            multiViewModel.checkClear.collectLatest {
                if(it){
                    viewModel.page = 1
                    viewModel.realMyData.clear()
                    exploreAdapter.testState.update { true }
                }
            }
        }


        resetFragment()
        observer()
        setViewComponents()
        getUserInput()





    }


    fun resetFragment() {
        lifecycleScope.launch {
            multiViewModel.checkClear.collectLatest { shouldReset ->
                if (shouldReset) {
                    findNavController().popBackStack(R.id.searchFragment,true)
                    findNavController().navigate(R.id.searchFragment)
                }
            }
        }


        lifecycleScope.launch {
            multiViewModel.checkClear.update { false }
        }

    }




    fun getUserInput(){


        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s.toString() == ""){
                    binding.feedRV.isVisible = true
                    binding.searchRV.isVisible = false
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.linearResult.isVisible = false



                if(s.toString() != ""){
                    binding.feedRV.isVisible = false
                    binding.searchRV.isVisible = true
                    binding.progressBar6.isVisible = true
                    viewModel.inputState.update { s.toString() }

                }
                else {
                    binding.feedRV.isVisible = true
                    binding.searchRV.isVisible = false
                    binding.linearResult.isVisible = false
                }
            }

        })



    }




    fun observer(){
        lifecycleScope.launch {
            val key = stringPreferencesKey("photoUri")
            dataStore.data.collectLatest {
                val uri = it[key]
                if(uri!=null){
                    binding.profilePhoto.setImageURI(uri.toUri())
                }
            }
        }

        var count = 0
        lifecycleScope.launch {
            viewModel.searchState.collectLatest {
                searchResultAdapter.updateAdapter(it)

                if(it.isEmpty() && count!= 0){
                    binding.linearResult.isVisible = true
                }
                else{
                    binding.linearResult.isVisible = false
                }
                count++
            }
        }


        lifecycleScope.launch {
            viewModel.inputState
                .debounce(1000)
                .filterNotNull()
                .collectLatest {queryString ->
                    viewModel.getSearchResult(queryString)
                    binding.progressBar6.isVisible = false
                }
        }


        lifecycleScope.launch {
            viewModel.isLoading.collectLatest {
                binding.progressBar6.isVisible = it
            }
        }






        lifecycleScope.launch {
            exploreAdapter.testState.collectLatest{
                if(it){
                    viewModel.getNewPage()
                }
            }
        }






        var string = "Select a genre"
        lifecycleScope.launch {
            multiViewModel.genreState.filterNotNull().collectLatest {
                Log.e("E",it)
                string = it
            }
        }

        lifecycleScope.launch{
            viewModel.feedState
                .filter { it.isNotEmpty() }
                .collectLatest {
                    val updatedDate = exploreAdapter.listUpdated(it,string)
                    viewModel.realMyData.addAll(updatedDate)
                    exploreAdapter.updateAdapter(viewModel.realMyData)
                }
        }

    }

    fun setViewComponents(){
        binding.feedRV.adapter = exploreAdapter
        binding.searchRV.adapter = searchResultAdapter
    }


    override fun buttonListener() {
        super.buttonListener()
        binding.filterButton.setOnClickListener {
            val filterFragment = FilterFragment()
            filterFragment.show(parentFragmentManager,"")
        }
    }
}