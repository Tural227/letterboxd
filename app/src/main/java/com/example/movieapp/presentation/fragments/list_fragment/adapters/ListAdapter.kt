package com.example.movieapp.presentation.fragments.list_fragment.adapters

import com.bumptech.glide.Glide
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.ListItemBinding
import com.example.movieapp.domain.model.ListItem

class ListAdapter (val onClick : (Int) -> Unit,val onLongClick : (Int,Int)->Unit):BaseAdapter<ListItem,ListItemBinding>(ListItemBinding::inflate) {
    override fun onBindViewHolder(holder: BaseViewHolder<ListItemBinding>, position: Int) {
        val current = myData[position]
        with(holder.binding){
            ratingBar2.rating = current.rating.toFloat()
            Glide.with(imageView14).load(current.poster).into(imageView14)
            textView42.text = current.movieName
            textView43.text = current.rating.toString()
            imageView14.setOnClickListener {
                onClick(current.movieID)
            }
        }
        holder.binding.imageView14.setOnLongClickListener {
            onLongClick(position,current.movieID)
            true
        }
    }
    fun deleteMovie(position: Int){
        myData.removeAt(position)
        notifyItemRemoved(position)
    }
}