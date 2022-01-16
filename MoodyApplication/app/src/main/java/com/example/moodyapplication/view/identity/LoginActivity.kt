package com.example.moodyapplication.view.identity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moodyapplication.databinding.ActivityLoginBinding
import com.example.moodyapplication.util.CHANNEL_ID
import com.example.moodyapplication.util.CreateNotification
import com.example.moodyapplication.view.main.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "LoginActivity"

private lateinit var binding: ActivityLoginBinding
private lateinit var progressDialog: ProgressDialog

private lateinit var notificationManager: NotificationManager
private val createNotification = CreateNotification()

lateinit var sharedPref: SharedPreferences
lateinit var sharedPrefEditor: SharedPreferences.Editor

class LoginActivity : AppCompatActivity(){

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // create progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)


        supportActionBar!!.hide()

        // condition for device version
        // calling channel function for notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createChannel()
                }

        // get create notification function from object of notification class
         createNotification.createNotification(this)

        // login button
        binding.loginButton.setOnClickListener {
            val email: String = binding.loginEmailedittext.text.toString()
            val password: String = binding.loginpasswordEdittext.text.toString()

            // condition to check if edit text of email and password not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {

                // showing progress dialog
                progressDialog.show()
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() {

                        if (it.isSuccessful) {

                            // shared preference editor
                            sharedPrefEditor = sharedPref.edit()
                            sharedPrefEditor.putBoolean("login", true)
                            sharedPrefEditor.commit()

                            Log.d(TAG, "shared $sharedPref")
                            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()

                            // navigate to main activity
                            val intent = Intent(this, MainActivity::class.java)
                            progressDialog.dismiss()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }
                    }
            }
        }

        // navigate to register activity if the user do not have account
        binding.createAccountTextview.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    // function for creating notification channel
    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID , "KOD Dev" , NotificationManager.IMPORTANCE_LOW)
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}