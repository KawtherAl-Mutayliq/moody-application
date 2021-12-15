package com.example.moodyapplication.view.main

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.view.isVisible
import androidx.work.Data
import com.bumptech.glide.Glide
import com.example.moodyapplication.databinding.ActivityMusicPlayBinding
import com.google.gson.Gson
import java.io.IOException

class MusicPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicPlayBinding

    val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = Gson().fromJson(intent.getStringExtra("musicModel"), Data::class.java)


    }
}