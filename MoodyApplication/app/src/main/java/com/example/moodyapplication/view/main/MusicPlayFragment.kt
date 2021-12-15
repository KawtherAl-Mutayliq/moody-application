package com.example.moodyapplication.view.main


import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.moodyapplication.databinding.FragmentMusicPlayBinding
import java.io.IOException



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"

private const val TAG = "MusicPlayFragment"

class MusicPlayFragment : Fragment(){
    private lateinit var param1: String
    private lateinit var param2: String
    private lateinit var param3: String
    private lateinit var param4: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1).toString()
            param2 = it.getString(ARG_PARAM2).toString()
            param3 = it.getString(ARG_PARAM3).toString()
            param4 = it.getString(ARG_PARAM4).toString()

            Log.d(TAG, "param3 ${it.getString(ARG_PARAM3).toString()}")
        }
    }

    private lateinit var binding: FragmentMusicPlayBinding
    val mediaPlayer = MediaPlayer()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       binding = FragmentMusicPlayBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameOfsong.text = param1
        binding.songDes.text = param2
        Glide.with(requireActivity()).load(Uri.parse(param3)).into(binding.musicImagView)

        try {
            mediaPlayer.setDataSource(param4)
            mediaPlayer.prepare()
        }catch (e: IOException){
            e.printStackTrace()
        }
        mediaPlayer.start()

        binding.pauseButton.setOnClickListener {
            pause()
        }

        binding.playButton.setOnClickListener {
            play()
        }
    }

    fun play(){
       mediaPlayer.start()
        binding.playButton.isVisible = false
        binding.pauseButton.isVisible = true
    }

    fun pause(){
        mediaPlayer.pause()
        binding.playButton.isVisible = true
        binding.pauseButton.isVisible = false
    }


        val runnable: Runnable = Runnable(){

        }


    companion object {
        @JvmStatic
        fun newInstance(name: String, des: String, photo:String, music: String) =
            MusicPlayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, name)
                    putString(ARG_PARAM2, des)
                    putString(ARG_PARAM3, photo)
                    putString(ARG_PARAM4, music)
                }
            }
    }
}


//        Thread(Runnable {
//            while (mediaPlayer != null){
//                val msg = Message()
//                msg.what = mediaPlayer.currentPosition
//                handler.sendMessage(msg)
//                Thread.sleep(1000)
//            }
//        })
//    }
//    var handler = @SuppressLint("HandlerLeak")
//    object :Handler(){
//        @SuppressLint("SetTextI18n")
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            val currentPosition = msg.what
//            binding.musicSeekBar.progress = currentPosition
//            val elapsedTime = createTimeLabel(currentPosition)
//            binding.startTime.text = elapsedTime
//
//            val remainingTime = createTimeLabel(totalTime - currentPosition)
//            binding.remainingTime.text = "-$remainingTime"
//        }
//    }
//
//    fun createTimeLabel(time: Int) : String{
//        var timeLabel = ""
//        val min =time/ 1000 / 60
//        val sec = time / 1000 * 60
//
//        timeLabel = "$min"
//
//        if (sec < 10) timeLabel += "0"
//        timeLabel += sec
//        return timeLabel


//musicSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, changed: Boolean) {
//                if (changed) {
//                    mediaPlayer.seekTo(progress)
//                }
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {}
//            override fun onStopTrackingTouch(p0: SeekBar?) {}
//        })