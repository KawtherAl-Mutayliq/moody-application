package com.example.moodyapplication.view.identity


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.moodyapplication.databinding.ActivityRegisterBinding
import com.example.moodyapplication.model.UserModel
import com.example.moodyapplication.util.CHANNEL_ID
import com.example.moodyapplication.util.CreateNotification
import com.example.moodyapplication.util.RegisterValidation
import com.example.moodyapplication.view.main.activities.MainActivity
import com.example.moodyapplication.view.main.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

private lateinit var binding: ActivityRegisterBinding
private lateinit var progressDialog: ProgressDialog

private val validator = RegisterValidation()

private lateinit var notificationManager: NotificationManager
private val createNotification = CreateNotification()

private var userModel = UserModel()

class RegisterActivity : AppCompatActivity() {

    private val viewModel : ProfileViewModel by viewModels()

    @SuppressLint("CommitPrefEdits")
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

        binding.registerButton.setOnClickListener {
            val email1 = binding.registerEmailedittext.text.toString()
            val password = binding.registerpasswordEdittext.text.toString()
            val firstName1 = binding.firstNameEdittext.text.toString()
            val lastName1 = binding.lastNameEdittext.text.toString()


            // condition to check if edit text of email and password not empty
            if (email1.isNotEmpty() && password.isNotEmpty() && firstName1.isNotEmpty()
                && lastName1.isNotEmpty()) {
                if (validator.emailValidation(email1)) {
                    if (validator.passwordValidation(password)) {
                        progressDialog.show()

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email1 , password)

                            .addOnCompleteListener() {
                                if (it.isSuccessful) {

                                    userModel.apply {
                                        firstName = firstName1
                                        lastName = lastName1
                                        email = email1
                                        viewModel.updateUserProfile(userModel)
                                    }
                                    // get create notification function from object of notification class
                                    createNotification.createNotification(this)

                                    // shared preference editor
                                    sharedPrefEditor = sharedPref.edit()
                                    sharedPrefEditor.putString(USERID, FirebaseAuth.getInstance().uid.toString())
                                    sharedPrefEditor.putBoolean("login", true)
                                    sharedPrefEditor.commit()

                                    Toast.makeText(
                                        this ,
                                        "User Registered Successfully" ,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    lifecycleScope.launch {
                                        
                                    }
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
                            "Make sure your password have symbols, small and capital letters and numbers " ,
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
            }else{
                Toast.makeText(
                    this ,
                    "Make sure you full blanks" ,
                    Toast.LENGTH_SHORT
                ).show()
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