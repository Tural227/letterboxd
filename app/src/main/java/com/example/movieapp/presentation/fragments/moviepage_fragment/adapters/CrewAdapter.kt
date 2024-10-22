package com.example.movieapp.presentation.fragments.moviepage_fragment.adapters

import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.CreditsProfileBinding
import com.example.movieapp.domain.model.PersonItem

class CrewAdapter(val onClick : (Int) -> Unit): BaseAdapter<PersonItem, CreditsProfileBinding>(CreditsProfileBinding::inflate) {
    override fun onBindViewHolder(holder: BaseViewHolder<CreditsProfileBinding>, position: Int) {
        val current = myData.getOrNull(position)

        current?.let {

            Glide.with(holder.binding.imageView21).load(current.profilePath).placeholder(R.drawable.pp).into(holder.binding.imageView21)
            holder.binding.imageView21.setOnClickListener { _->
                onClick(it.personID)
            }
        }
    }
}