package com.example.moodyapplication.view.main

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.media.app.NotificationCompat
import com.bumptech.glide.Glide
import com.example.moodyapplication.databinding.ActivityMusicPlayBinding
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.view.main.viewmodel.PlaylistViewModel
import java.io.IOException
import kotlin.concurrent.thread
import android.media.Ringtone
import java.lang.Exception


private const val TAG = "MusicPlayActivity"

class MusicPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicPlayBinding

    var mediaPlayer = MediaPlayer()

    val handler = Handler()

    private lateinit var out: String
    private lateinit var out2: String

    private var difference = 0
    private var position = 0

    val viewModel: PlaylistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        position = intent.getIntExtra("position" , 0)

        val list: List<MusicModel> = intent.getParcelableArrayListExtra("list")!!

        val name = intent.getStringExtra("name")!!
        val description = intent.getStringExtra("description")!!
        val photo = intent.getStringExtra("photo")!!
        val music = intent.getStringExtra("music")!!



        initMediaPlayer(name , description , photo , music)

        binding.pauseButton.setOnClickListener {
            pause()
        }

        binding.playButton.setOnClickListener {
            play()
        }
        binding.closeButton.setOnClickListener {
            finish()
            mediaPlayer.stop()
        }
        binding.forwardButton.setOnClickListener {
            if (position < list.size - 1) {
                position++
            } else {
                position = 0
            }

            initMediaPlayer(
                list[position].name ,
                list[position].description ,
                list[position].photo ,
                list[position].music
            )

        }

        binding.rewindButton.setOnClickListener {
            if (position <= 0) {
                position = list.size - 1
            } else {
                position--
            }

            initMediaPlayer(
                list[position].name ,
                list[position].description ,
                list[position].photo ,
                list[position].music
            )

        }

        initializedSeekBar()
        initializedVolumeSeekBar()


        binding.musicSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar? , progress: Int , fromUser: Boolean) {

                if (fromUser) {
                    mediaPlayer.seekTo(progress * 1000)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun initializedSeekBar() {
        binding.musicSeekBar.max = mediaPlayer.duration / 1000
        thread {

            runOnUiThread(object : Runnable {
                @SuppressLint("SetTextI18n")
                override fun run() {
                    val currentPosition = mediaPlayer.currentPosition / 1000
                    binding.musicSeekBar.progress = currentPosition

                    out = String.format(
                        "%02d:%02d" ,
                        binding.musicSeekBar.progress / 60 ,
                        binding.musicSeekBar.progress % 60
                    )

                    binding.startTime.text = out

                    difference = mediaPlayer.duration / 1000 - mediaPlayer.currentPosition / 1000
                    out2 = String.format(
                        "%02d:%02d" ,
                        difference / 60 ,
                        difference % 60
                    )

                    binding.musicTime.text = "-$out2"
                    handler.postDelayed(this , 1000)
                }

            })
        }
    }

    private fun initializedVolumeSeekBar() {
        binding.volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar? , progress: Int , fromUser: Boolean) {
                if (fromUser) {
                    var volumeNum = progress / 100.0f
                    mediaPlayer.setVolume(volumeNum , volumeNum)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
    }

    private fun play() {
        mediaPlayer.start()
        binding.playButton.isVisible = false
        binding.pauseButton.isVisible = true
    }

    fun pause() {
        mediaPlayer.pause()
        binding.playButton.isVisible = true
        binding.pauseButton.isVisible = false
    }

    private fun initMediaPlayer(
        name: String ,
        description: String ,
        photo: String ,
        music: String
    ) {
        binding.nameOfsong.text = name
        binding.songDes.text = description
        Glide.with(this).load(photo).into(binding.musicImagView)

        mediaPlayer.setVolume(0.5f , 0.5f)

        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(this , Uri.parse(music))
            mediaPlayer.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaPlayer.start()
    }

}