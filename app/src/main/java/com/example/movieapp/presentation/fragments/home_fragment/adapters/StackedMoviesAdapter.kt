package com.example.movieapp.presentation.fragments.home_fragment.adapters


import com.bumptech.glide.Glide
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.PopularListBinding
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.NavigationItem
import com.example.movieapp.domain.model.SecondaryMovieItem


class StackedMoviesAdapter(val navigation: (NavigationItem,Int)-> Unit) : BaseAdapter<List<SecondaryMovieItem>, PopularListBinding>(PopularListBinding::inflate) {


    override fun onBindViewHolder(holder: BaseViewHolder<PopularListBinding>, position: Int) {
        val current = myData.getOrNull(position)

        current?.let {
            with(current){
                with(holder.binding){
                    Glide.with(firstImageView).load(current[0].moviePoster).into(firstImageView)
                    Glide.with(firstImageView).load(current[1].moviePoster).into(secondImageView)
                    Glide.with(firstImageView).load(current[2].moviePoster).into(thirdImageView)
                    Glide.with(firstImageView).load(current[3].moviePoster).into(fourthImageView)
                    titleTextView.text = "List ${position + 1}"
                }
            }

            holder.binding.root.setOnClickListener {
                navigation(
                    NavigationItem(
                        itemOne = current[0],
                        itemTwo = current[1],
                        itemThree = current[2],
                        itemFour = current[3]
                    ), position + 1
                )
            }

        }


    }

}