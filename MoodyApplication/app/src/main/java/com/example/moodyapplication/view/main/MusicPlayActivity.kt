package com.example.moodyapplication.view.main

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaDescription
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.moodyapplication.databinding.ActivityMusicPlayBinding
import com.example.moodyapplication.model.MusicModel
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize
import java.io.IOException
import java.lang.Exception
import kotlin.concurrent.thread

private const val TAG = "MusicPlayActivity"
class MusicPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicPlayBinding

    var mediaPlayer = MediaPlayer()

    val handler = Handler()

    private lateinit var out: String
    private lateinit var out2: String

    private var difference = 0
    private var position = 0


    private lateinit var musicArrayList: ArrayList<MusicModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)


        position = intent.getIntExtra("position", 0)
       musicArrayList= intent.getParcelableArrayListExtra<MusicModel>("musicModel") as ArrayList<MusicModel>


        musicArrayList.forEach {
            initMediaPlayer(it.name, it.description, it.photo, it.music)
        }



        binding.pauseButton.setOnClickListener {
            pause()
        }

        binding.playButton.setOnClickListener {
            play()
        }
        binding.closeButton.setOnClickListener {
            finish()
        }
        binding.forwardButton.setOnClickListener {
            if (position < musicArrayList.size - 1){
                position++
            }else{
                position = 0
            }

            musicArrayList.forEach {
                initMediaPlayer(it.name, it.description, it.photo, it.music)
            }

        }

        binding.rewindButton.setOnClickListener {
            if (position <= 0){
                position = musicArrayList.size -1
            }else{
                position--
            }

            musicArrayList.forEach {
                initMediaPlayer(it.name, it.description, it.photo, it.music)
            }

        }

        initializedSeekBar()

        binding.musicSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {

                if (fromUser) { mediaPlayer.seekTo(progress * 1000) }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun initializedSeekBar() {
        binding.musicSeekBar.max = mediaPlayer.duration / 1000
        thread{

            runOnUiThread(object :Runnable {
                @SuppressLint("SetTextI18n")
                override fun run() {
                    val currentPosition = mediaPlayer.currentPosition/1000
                    binding.musicSeekBar.progress = currentPosition

                    out = String.format("%02d:%02d",
                        binding.musicSeekBar.progress/60,
                        binding.musicSeekBar.progress % 60)

                    binding.startTime.text = out

                    difference = mediaPlayer.duration/1000 - mediaPlayer.currentPosition/1000
                    out2 = String.format("%02d:%02d",
                        difference /60,
                        difference % 60)

                    binding.musicTime.text = "-$out2"
                    handler.postDelayed(this, 1000)
                }

            })
        }
    }

    private fun play(){
        mediaPlayer.start()
        binding.playButton.isVisible = false
        binding.pauseButton.isVisible = true
    }

    fun pause(){
        mediaPlayer.pause()
        binding.playButton.isVisible = true
        binding.pauseButton.isVisible = false
    }

    private  fun  initMediaPlayer(name : String, description: String, photo: String, music: String){
         binding.nameOfsong.text = name
         binding.songDes.text = description
         Glide.with(this).load(photo).into(binding.musicImagView)

         try {
             mediaPlayer.reset()
            mediaPlayer.setDataSource(this, Uri.parse(music))
             mediaPlayer.prepare()
         }catch (e: IOException){
             e.printStackTrace()
         }
         mediaPlayer.start()
    }
}