package com.example.movieapp.presentation.fragments.search_fragment.adapters

import com.bumptech.glide.Glide
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.OneImageBinding
import com.example.movieapp.domain.model.MovieItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt

class SearchResultAdapter(val navigation: (Int)-> Unit): BaseAdapter<MovieItem,OneImageBinding>(OneImageBinding::inflate) {




    override fun onBindViewHolder(holder: BaseViewHolder<OneImageBinding>, position: Int) {
        val current = myData[position]
        Glide.with(holder.binding.posterImageView).load(current.poster).into(holder.binding.posterImageView)
        holder.binding.nameTextView.text = current.movieName
        holder.binding.ratingTextView.text = ((current.rating*10/2).roundToInt()/10f).toString()
        holder.binding.overivewTextView.text = current.overview


        holder.binding.root.setOnClickListener {
            navigation(current.id)
        }


    }
}