package com.example.movieapp.presentation.fragments.likes_fragment

import com.bumptech.glide.Glide
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.LikesItemBinding
import com.example.movieapp.domain.model.MovieItem

class LikesAdapter (val onClick : (Int)->Unit, val onLongClick : (Int, Int)->Unit   ) : BaseAdapter<MovieItem,LikesItemBinding>(LikesItemBinding::inflate) {
    override fun onBindViewHolder(holder: BaseViewHolder<LikesItemBinding>, position: Int) {
        val current = myData[position]
        Glide.with(holder.binding.imageView31).load(current.poster).into(holder.binding.imageView31)
        holder.binding.textView44.text = current.movieName
        holder.binding.root.setOnClickListener {
            onClick(current.id)
        }
        holder.binding.root.setOnLongClickListener {
            onLongClick(position,current.id)

            true
        }



    }

    fun deleteMovie(position: Int){
        myData.removeAt(position)
        notifyItemRemoved(position)
    }

}