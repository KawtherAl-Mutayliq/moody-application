package com.example.moodyapplication.view.main

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.moodyapplication.databinding.ActivityMusicPlayBinding
import java.io.IOException
import kotlin.concurrent.thread
import android.os.Build
import com.example.moodyapplication.util.*
import android.media.AudioManager

import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager.OnAudioFocusChangeListener
import androidx.annotation.RequiresApi
import com.example.moodyapplication.model.MusicModel


private const val TAG = "MusicPlayActivity"

class MusicPlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicPlayBinding

    var mediaPlayer = MediaPlayer()

    val handler = Handler()

    private lateinit var out: String
    private lateinit var out2: String

    private var difference = 0
    private var position = 0

    lateinit var list: MutableList<MusicModel>

    private lateinit var notificationManager: NotificationManager
    private val createNotification = CreateNotification()

    private var audioFocusRequest: Int = 0


    // Audio manager instance to manage or
    // handle the audio interruptions
    var audioManager: AudioManager? = null

    // media player is handled according to the
    // change in the focus which Android system grants for
    var audioFocusChangeListener =
        OnAudioFocusChangeListener { focusChange ->
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start()
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mediaPlayer.pause()
                mediaPlayer.seekTo(0)
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mediaPlayer.release()
            }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        position = intent.getIntExtra("position" , 0)
        list = intent.getParcelableArrayListExtra("list")!!

        val name = intent.getStringExtra("name")!!
        val description = intent.getStringExtra("description")!!
        val photo = intent.getStringExtra("photo")!!
        val music = intent.getStringExtra("music")!!


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createChannel()
//        }

       // createNotification.createNotification(this , list , position , list.size - 1)


        initMediaPlayer(name , description , photo , music)


        binding.pauseButton.setOnClickListener {
            pause()
        }

        binding.playButton.setOnClickListener {

            play()

            if (audioFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mediaPlayer.start()
            }

        }
        binding.closeButton.setOnClickListener {
            finish()
            mediaPlayer.stop()
        }
        binding.forwardButton.setOnClickListener {
            next()
        }

        binding.rewindButton.setOnClickListener {
            previous()
        }

        mediaPlayer.setOnCompletionListener{
            next()
        }

        initializedSeekBar()


        binding.musicSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar? , progress: Int , fromUser: Boolean) {

                if (fromUser) {
                    mediaPlayer.seekTo(progress * 1000)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })


        // get the audio system service for
        // the audioManger instance
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Audio attributes instance to set the playback
        // attributes for the media player instance
        // these attributes specify what type of media is
        // to be played and used to callback the audioFocusChangeListener
        val playbackAttributes1 = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build();

        // set the playback attributes for the focus requester
        val focusRequest: AudioFocusRequest =
            AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes1)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .build();

        // request the audio focus and
        // store it in the int variable
        audioFocusRequest = audioManager!!.requestAudioFocus(focusRequest)

    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID , "KOD Dev" , NotificationManager.IMPORTANCE_LOW)
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
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

    private fun play() {
        mediaPlayer.start()
        binding.playButton.isVisible = false
        binding.pauseButton.isVisible = true
    }

    private fun pause() {

        mediaPlayer.pause()
        binding.playButton.isVisible = true
        binding.pauseButton.isVisible = false
    }

    private fun previous() {


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

    private fun next() {

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