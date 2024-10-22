package com.example.movieapp.common.base

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<Type,VB: ViewBinding>(val inflate: (LayoutInflater, ViewGroup, Boolean)-> VB): RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>>() {
    class BaseViewHolder<VB: ViewBinding>(val binding: VB): RecyclerView.ViewHolder(binding.root)


    var myData = mutableListOf<Type>()

    open fun updateAdapter(myNewData: List<Type>){

        myData = myNewData.toMutableList()
        notifyDataSetChanged()///////////////////
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        return  BaseViewHolder(inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {

        return myData.size
    }

    abstract override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int)

}


