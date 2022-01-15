package com.example.moodyapplication.view.main.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.moodyapplication.R
import com.example.moodyapplication.repository.ApiServiceRepository
import com.example.moodyapplication.view.identity.LoginActivity
import com.example.moodyapplication.view.identity.sharedPref

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ApiServiceRepository.init(this)
        supportActionBar!!.hide()

        fun startMainActivity() {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        fun startLogInFragment() {
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        Handler().postDelayed({
            sharedPref = getSharedPreferences("shared", Context.MODE_PRIVATE)

            if (sharedPref.getBoolean("login", false)){
                startMainActivity()
            }else{
                startLogInFragment()
            }
        }, 2000)
    }
}