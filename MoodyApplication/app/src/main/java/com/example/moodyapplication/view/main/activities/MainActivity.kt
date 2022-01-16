package com.example.moodyapplication.view.main.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moodyapplication.R
import com.example.moodyapplication.databinding.ActivityMainBinding
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.view.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FragmentAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // make object of fragment adapter
        adapter = FragmentAdapter(supportFragmentManager , lifecycle)
        binding.viewPager.adapter = adapter

        // but titles and icons for each fragment in tablayout
        TabLayoutMediator(binding.tabLayout , binding.viewPager) { tab , position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_baseline_music_note_24)
                    tab.text = "music"
                }
                1 -> {
                    tab.setIcon(R.drawable.sad)
                    tab.text = "sad"
                }
                2 -> {
                    tab.setIcon(R.drawable.happy)
                    tab.text = "happy"
                }
                3 -> {
                    tab.setIcon(R.drawable.novel)
                    tab.text = "romance"
                }
                4 -> {
                    tab.setIcon(R.drawable.workout)
                    tab.text = "workout"
                }
                5 -> {
                    tab.setIcon(R.drawable.ic_baseline_favorite_24)
                    tab.text = "favorite"
                }
            }
        }.attach()
    }
}