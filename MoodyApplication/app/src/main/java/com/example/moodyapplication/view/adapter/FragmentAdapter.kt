package com.example.moodyapplication.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moodyapplication.view.main.*


class FragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle){
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PlaylistFragment()
            1 -> SadMoodFragment()
            2 -> HappyMoodFragment()
            3 -> RoanceMoodFragment()
            4 -> WorkoutMoodFragment()
            else -> Fragment()
        }
    }


}