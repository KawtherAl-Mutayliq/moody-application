package com.example.moodyapplication.view.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moodyapplication.view.main.*

class PageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
       return 4
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> { return PlaylistFragment() }
            1 -> { return SadMoodFragment() }
            2 -> { return HappyMoodFragment() }
            3 -> { return RoanceMoodFragment() }
            4 -> { return WorkoutMoodFragment() }
            else -> {return PlaylistFragment() }
        }
    }

}