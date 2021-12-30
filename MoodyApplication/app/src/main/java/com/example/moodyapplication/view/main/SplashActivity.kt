package com.example.moodyapplication.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.moodyapplication.R
import com.example.moodyapplication.repository.ApiServiceRepository
import com.example.moodyapplication.view.identity.LoginActivity
import com.google.firebase.auth.FirebaseAuth

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
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null){
                startMainActivity()
            }else{
                startLogInFragment()
            }
        }, 2000)
    }
}