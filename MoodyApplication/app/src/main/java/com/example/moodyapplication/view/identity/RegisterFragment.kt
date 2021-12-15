package com.example.moodyapplication.view.identity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moodyapplication.R
import com.example.moodyapplication.database.DataBase
import com.example.moodyapplication.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val email = binding.registerEmailedittext.text
            val password = binding.registerpasswordEdittext.text

            DataBase.myRef.push().setValue(email)
            DataBase.myRef.push().setValue(password)
        }

        binding.gobackTextView.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.login_layout, RegisterFragment())
                .commit()
        }
    }

}