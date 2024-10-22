package com.example.movieapp.presentation.fragments.user_profile.background_fragment.adapters

import android.util.Log
import com.bumptech.glide.Glide
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.BackgroundItemBinding
import com.example.movieapp.domain.model.BackgroundItem

class BackgroundAdapter (val onClick : (String)->Unit ): BaseAdapter<BackgroundItem,BackgroundItemBinding>(BackgroundItemBinding::inflate) {
    override fun onBindViewHolder(holder: BaseViewHolder<BackgroundItemBinding>, position: Int) {
        val current = myData.getOrNull(position)

        current?.let {
            Glide.with(holder.binding.imageView33).load(current.backPoster).into(holder.binding.imageView33)
            holder.binding.movieNameTextView.text = current.movieName


            holder.binding.root.setOnClickListener {
                onClick(current.backPoster)
            }

        }
    }



}