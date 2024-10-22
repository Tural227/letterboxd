package com.example.movieapp.presentation.fragments.moviepage_fragment.tab_layout.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.lifecycle.Lifecycle
import com.example.movieapp.presentation.fragments.moviepage_fragment.tab_layout.fragments.DetailsCastFragment
import com.example.movieapp.presentation.fragments.moviepage_fragment.tab_layout.fragments.DetailsCrewFragment
import com.example.movieapp.presentation.fragments.moviepage_fragment.tab_layout.fragments.DetailsScreenFragment

class FragmentPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager,lifecycle)
{
    private val fragments = listOf(
        DetailsCastFragment(),
        DetailsCrewFragment(),
        DetailsScreenFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }

}