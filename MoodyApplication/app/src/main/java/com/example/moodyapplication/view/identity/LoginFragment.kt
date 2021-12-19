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
import com.example.moodyapplication.databinding.FragmentLoginBinding
import com.example.moodyapplication.view.main.MainActivity
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Loading...")
        progressDialog.setCancelable(false)

        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val email: String = binding.loginEmailedittext.text.toString()
            val password: String = binding.loginpasswordEdittext.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                progressDialog.show()
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() {
                        if (it.isSuccessful) {
                            Toast.makeText(requireActivity(), "Login successfully", Toast.LENGTH_SHORT).show()

                            // navigate to main activity
                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            Toast.makeText(requireActivity(), it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.createAccountTextview.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.register_layout, RegisterFragment())
                .commit()
        }
    }
}