package com.example.moodyapplication.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.moodyapplication.R
import com.example.moodyapplication.repository.ApiServiceRepository

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ApiServiceRepository.init(this)
        supportActionBar!!.hide()

        val intent = Intent(this, MainActivity::class.java)
        val timer = object : CountDownTimer(2000, 1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                startActivity(intent)
                finish()
            }

        }
        timer.start()
    }

}