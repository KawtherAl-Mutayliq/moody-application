package com.example.moodyapplication.view.identity


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moodyapplication.database.DataBase
import com.example.moodyapplication.databinding.ActivityRegisterBinding
import com.example.moodyapplication.model.UserModel
import com.example.moodyapplication.util.RegisterValidation
import com.example.moodyapplication.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding : ActivityRegisterBinding
private lateinit var progressDialog: ProgressDialog
private val validator = RegisterValidation()
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)


        supportActionBar!!.hide()

        binding.registerButton.setOnClickListener {
            val email = binding.registerEmailedittext.text.toString()
            val password = binding.registerpasswordEdittext.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (validator.emailValidation(email)) {
                    if (validator.passwordValidation(password)) {
                        progressDialog.show()
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email , password)
                            .addOnCompleteListener() {
                                if (it.isSuccessful) {
                                    val userModel = UserModel(email , password)
                                    val userInformation =
                                        DataBase.userCollection.document(FirebaseAuth.getInstance().currentUser!!.uid)
                                    userInformation.set(userModel)
                                    Toast.makeText(
                                        this ,
                                        "User Registered Successfully" ,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
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
                            "Make sure your password is Strong" , Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Make sure you typed your e-mail address correctly" , Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.gobackTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}