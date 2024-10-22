package com.example.movieapp.presentation.fragments.user_reviews_fragment


import android.net.Uri
import android.text.Html
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.common.base.BaseAdapter
import com.example.movieapp.databinding.RecentViewsBinding
import com.example.movieapp.domain.model.UserReviewsItem

class ReviewsAdapter(val onClick: (Int)->Unit, val onLongClick: (Int,Int) -> Unit): BaseAdapter<UserReviewsItem, RecentViewsBinding> (RecentViewsBinding::inflate){
    var uri : Uri? = null

    override fun onBindViewHolder(holder: BaseViewHolder<RecentViewsBinding>, position: Int) {
        with(holder.binding){
            val current = myData.getOrNull(position)
            current?.let {
                if(uri != null){
                    ppImageView.setImageURI(uri)

                }
                Glide.with(moiveImageView).load(current.moviePoster).into(moiveImageView)
                usernameTextView.text = Html.fromHtml("Review by <font color = \"#E9A6A6\">${current.name}</font>")
                movieNameTextView.text = current.movieName
                ratingBar3.rating = current.rating /2

                yearTextView.text = current.year.split("-").first()
                reviewTextView.text = current.review



                holder.binding.root.setOnLongClickListener {
                    onLongClick(current.databaseId,position)

                    true
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

    fun deleteReview(position: Int){
        myData.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getImageUri(uri : Uri){
        this.uri = uri
        notifyDataSetChanged()
    }

}