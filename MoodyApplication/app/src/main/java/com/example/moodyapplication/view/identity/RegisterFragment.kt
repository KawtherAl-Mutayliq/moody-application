package com.example.moodyapplication.view.identity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moodyapplication.R
import com.example.moodyapplication.database.DataBase
import com.example.moodyapplication.databinding.FragmentRegisterBinding
import com.example.moodyapplication.model.UserModel
import com.example.moodyapplication.util.RegisterValidation
import com.example.moodyapplication.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val validator = RegisterValidation()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)

        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val email = binding.registerEmailedittext.text.toString()
            val password = binding.registerpasswordEdittext.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (validator.emailValidation(email)){
                    if (validator.passwordValidation(password)){
                        progressDialog.show()
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener() {
                                if (it.isSuccessful) {
                                    val userModel = UserModel(email, password)
                                    val userInformation = DataBase.userCollection.document(FirebaseAuth.getInstance().currentUser!!.uid)
                                    userInformation.set(userModel)
                                    Toast.makeText(
                                        requireActivity(), "User Registered Successfully", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(requireActivity(), MainActivity::class.java)
                                    startActivity(intent)
                                    requireActivity().finish()
                                } else {
                                    Toast.makeText(requireActivity(), it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                    }else{
                        Toast.makeText(requireActivity(),
                            "Make sure your password is Strong", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireActivity(),
                        "Make sure you typed your e-mail address correctly", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.gobackTextView.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.login_layout, RegisterFragment())
                .commit()
        }
    }

}