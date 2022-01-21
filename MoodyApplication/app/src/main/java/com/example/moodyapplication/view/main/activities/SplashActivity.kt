package com.example.moodyapplication.view.main.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.moodyapplication.R
import com.example.moodyapplication.repository.ApiServiceRepository
import com.example.moodyapplication.repository.FireBaseRepository
import com.example.moodyapplication.view.identity.LoginActivity
import com.example.moodyapplication.view.identity.sharedPref

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // initialized service repository
        ApiServiceRepository.init(this)
        FireBaseRepository.init(this)

        // hide action bar of splash activity
        supportActionBar!!.hide()

        // function to open main activity
        fun startMainActivity() {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // function to open login activity
        fun startLogInFragment() {
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // condition for start app
        // if user is login will open main activity if not will open login activity
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