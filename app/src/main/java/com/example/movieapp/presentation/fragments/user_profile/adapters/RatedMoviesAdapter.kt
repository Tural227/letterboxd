package com.example.movieapp.presentation.fragments.user_profile.adapters

import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.RecentReviewedItemBinding
import com.example.movieapp.domain.model.RatingItem
import com.squareup.picasso.Picasso

class RatedMoviesAdapter(val onClick : (Int)-> Unit): BaseAdapter<RatingItem,RecentReviewedItemBinding>(
    RecentReviewedItemBinding::inflate) {
    override fun onBindViewHolder(holder: BaseViewHolder<RecentReviewedItemBinding>, position: Int) {
        val current = myData.getOrNull(position)
        with(holder.binding){

            current?.let {
                Picasso.get().load(current.poster).into(posterImage)
                ratingBar4.rating = current.rating
                posterImage.setOnClickListener {
                    onClick(current.movieID)
                }
            }
        }
    }
    var isCollapsed = true



    fun changeCollapse(){
        this.isCollapsed = this.isCollapsed.not()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        return if (isCollapsed) myData.size.coerceAtMost(5) else myData.size
    }


}