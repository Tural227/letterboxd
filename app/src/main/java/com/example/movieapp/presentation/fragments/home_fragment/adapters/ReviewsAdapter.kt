package com.example.movieapp.presentation.fragments.home_fragment.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.text.Html
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide

import com.example.movieapp.R
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.RecentViewsBinding
import com.example.movieapp.domain.model.MovieItem
import com.example.movieapp.domain.model.UserReviewsItem
import com.squareup.picasso.Picasso

class ReviewsAdapter(val onClick: (Int)->Unit): BaseAdapter<UserReviewsItem, RecentViewsBinding> (RecentViewsBinding::inflate){
    @SuppressLint("SetTextI18n")

    var uri : Uri? = null

    override fun onBindViewHolder(holder: BaseViewHolder<RecentViewsBinding>, position: Int) {
        with(holder.binding){
            val current = myData.getOrNull(position)
            current?.let {
                if(it.avatar == "" && uri != null){
                    holder.binding.ppImageView.setImageURI(uri)
                }
                else{
                    Glide.with(ppImageView).load(current.avatar).error(R.drawable.pp).into(ppImageView)
                }
                Glide.with(moiveImageView).load(current.moviePoster).into(moiveImageView)
                usernameTextView.text = Html.fromHtml("Review by <font color = \"#E9A6A6\">${current.name}</font>")
                movieNameTextView.text = current.movieName
                ratingBar3.rating = current.rating /2

                yearTextView.text = current.year.split("-").first()
                reviewTextView.text = current.review
            }
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

            moiveImageView.setOnClickListener {
                current?.id?.let { it1 -> onClick(it1) }
            }


        }
    }


    fun addUserReviews(userReviewsItem: UserReviewsItem){
        myData.add(userReviewsItem)
        notifyDataSetChanged()
    }

    fun getImageUri(uri : Uri){
        this.uri = uri
        notifyDataSetChanged()
    }


}