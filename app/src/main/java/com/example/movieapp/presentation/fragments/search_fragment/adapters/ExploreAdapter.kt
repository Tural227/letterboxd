package com.example.movieapp.presentation.fragments.search_fragment.adapters



import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.data.remote.model.movie_genre.Genre
import com.example.movieapp.data.remote.myapi.MovieApi
import com.example.movieapp.databinding.OneImageBinding
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt


class ExploreAdapter(val navigation : (Int)-> Unit) : BaseAdapter<MovieItem, OneImageBinding>(OneImageBinding::inflate) {


    val testState = MutableStateFlow(true)

    val page = MutableLiveData(1)

    var genre : List<Genre> = mutableListOf()








    override fun onBindViewHolder(holder: BaseViewHolder<OneImageBinding>, position: Int) {
        if(myData.isNotEmpty()){


            val current = myData[position]

            holder.binding.root.setOnClickListener{
                navigation(current.id)
            }

            Glide.with(holder.binding.posterImageView).load(current.poster).into(holder.binding.posterImageView)
            holder.binding.nameTextView.text = current.movieName
            holder.binding.ratingTextView.text = ((current.rating*10/2).roundToInt()/10f).toString()
            holder.binding.overivewTextView.text = current.overview


            if(position == myData.size - 4){

                testState.update { true }
            }

            Log.e("LIST",myData.size.toString())

        }

    }






    fun listUpdated(dataList: List<MovieItem>,genreName: String) : List<MovieItem>{
        val genreId = genre.find { it.name == genreName }?.id ?: 0
        var secondList = dataList.toMutableList()
        if (genreName != "Select a genre") {
            secondList = secondList.filter { it.genres.contains(genreId) }.toMutableList()
        }
        secondList = secondList.distinct().toMutableList()
        testState.update { false }
        return secondList
    }



}