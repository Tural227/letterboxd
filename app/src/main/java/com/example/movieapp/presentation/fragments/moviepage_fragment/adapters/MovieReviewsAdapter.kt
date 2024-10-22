package com.example.movieapp.presentation.fragments.moviepage_fragment.adapters

import android.net.Uri
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.MoviePageReviewBinding
import com.example.movieapp.domain.model.UserReviewsItem
import com.example.movieapp.R

class MovieReviewsAdapter: BaseAdapter<UserReviewsItem, MoviePageReviewBinding>(MoviePageReviewBinding::inflate) {

    var uri : Uri? = null

    override fun onBindViewHolder(holder: BaseViewHolder<MoviePageReviewBinding>, position: Int) {
        val current = myData.getOrNull(position)

        current?.let {
            with(holder.binding){
                if(current.avatar == "" && uri!=null){
                    this.ppImageView.setImageURI(uri)
                }
                else{
                    Glide.with(ppImageView).load(current.avatar).error(R.drawable.pp).into(ppImageView)
                }
                holder.binding.reviewTextView.text = current.review
                holder.binding.usernameTextView.text = current.name
                holder.binding.ratingBar3.rating = current.rating/2f

                expandTextView.post {
                    val layout = reviewTextView.layout
                    val lines = layout.lineCount.also{
                        Log.e("lines",it.toString())
                    }
                    if(lines > 0){
                        if(layout.getEllipsisCount(lines - 1) > 0){
                            expandTextView.visibility = View.VISIBLE
                        }
                        else{
                            expandTextView.visibility = View.GONE
                        }
                    }

                }
                var flag = true
                expandTextView.setOnClickListener {
                    if(flag){
                        reviewTextView.maxLines = Integer.MAX_VALUE
                        expandTextView.text = "Read less <"
                        flag = false
                    }
                    else{
                        expandTextView.text = "Read more >"
                        reviewTextView.maxLines = 4
                        flag = true
                    }

                }
            }

        }

    }



    fun getUserReviews(userReviewsItem: UserReviewsItem){
        myData.add(userReviewsItem)
        notifyDataSetChanged()
    }

    fun getImageUri(uri: Uri){
        this.uri = uri
        notifyDataSetChanged()
    }

}