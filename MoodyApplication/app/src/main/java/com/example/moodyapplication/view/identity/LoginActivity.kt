package com.example.moodyapplication.view.identity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moodyapplication.databinding.ActivityLoginBinding
import com.example.moodyapplication.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuth


private lateinit var binding: ActivityLoginBinding
private lateinit var progressDialog: ProgressDialog

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)


        supportActionBar!!.hide()

        binding.loginButton.setOnClickListener {
            val email: String = binding.loginEmailedittext.text.toString()
            val password: String = binding.loginpasswordEdittext.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                progressDialog.show()
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()

                            // navigate to main activity
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.createAccountTextview.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}