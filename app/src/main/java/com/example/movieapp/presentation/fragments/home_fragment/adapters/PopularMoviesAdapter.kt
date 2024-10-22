package com.example.movieapp.presentation.fragments.home_fragment.adapters


import com.bumptech.glide.Glide
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.OneMovieBinding
import com.example.movieapp.domain.model.SecondaryMovieItem




class PopularMoviesAdapter(val onClick: (Int)-> Unit) : BaseAdapter<SecondaryMovieItem,OneMovieBinding>(OneMovieBinding::inflate) {
    override fun onBindViewHolder(holder: BaseViewHolder<OneMovieBinding>, position: Int) {
        val current = myData[position]
        Glide.with(holder.binding.posterImageView).load(current.moviePoster).into(holder.binding.posterImageView)
        holder.binding.root.setOnClickListener {
            onClick(current.movieID)
        }
    }
}