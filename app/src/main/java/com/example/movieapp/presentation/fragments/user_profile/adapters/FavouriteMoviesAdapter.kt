package com.example.movieapp.presentation.fragments.user_profile.adapters

import com.bumptech.glide.Glide
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.data.local.FavouriteEntity
import com.example.movieapp.databinding.OneMovieBinding
import com.example.movieapp.domain.model.MovieItem

class FavouriteMoviesAdapter(val onClick : (Int)-> Unit): BaseAdapter<MovieItem, OneMovieBinding>(OneMovieBinding::inflate){
    override fun onBindViewHolder(holder: BaseViewHolder<OneMovieBinding>, position: Int) {
        val current = myData.getOrNull(position)
        with(holder.binding){
            current?.let { current->
                Glide.with(posterImageView).load(current.poster).into(posterImageView)
                posterImageView.setOnClickListener {
                    onClick(current.id)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return if(myData.size <= 4) myData.size else 4
    }



}