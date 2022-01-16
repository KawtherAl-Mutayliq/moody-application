package com.example.moodyapplication.view.identity


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moodyapplication.databinding.ActivityRegisterBinding
import com.example.moodyapplication.util.CHANNEL_ID
import com.example.moodyapplication.util.CreateNotification
import com.example.moodyapplication.util.RegisterValidation
import com.example.moodyapplication.view.main.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding: ActivityRegisterBinding
private lateinit var progressDialog: ProgressDialog

private val validator = RegisterValidation()

private lateinit var notificationManager: NotificationManager
private val createNotification = CreateNotification()

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

        binding.registerButton.setOnClickListener {
            val email = binding.registerEmailedittext.text.toString()
            val password = binding.registerpasswordEdittext.text.toString()

            // condition to check if edit text of email and password not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (validator.emailValidation(email)) {
                    if (validator.passwordValidation(password)) {
                        progressDialog.show()
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email , password)
                            .addOnCompleteListener() {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        this ,
                                        "User Registered Successfully" ,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this , MainActivity::class.java)
                                    progressDialog.dismiss()
                                    startActivity(intent)
                                    finish()
                                } else {
                                    progressDialog.dismiss()
                                    Toast.makeText(
                                        this ,
                                        it.exception!!.message.toString() ,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this ,
                            "Make sure your password is Strong" ,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this ,
                        "Make sure you typed your e-mail address correctly" ,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // navigate to login activity
        binding.gobackTextView.setOnClickListener {
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
        }

    }


    // function for creating notification channel
    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID , "KOD Dev" , NotificationManager.IMPORTANCE_LOW)
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}